package com.invest.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

/**
 * Serviço para cálculos de inflação e valores deflacionados
 * Utiliza IPCA (Índice Nacional de Preços ao Consumidor Amplo) como referência
 */
@Service
public class InflacaoService {

    // Taxa de inflação mensal média aproximada do IPCA (pode ser substituída por dados reais)
    // Valores em percentual mensal (ex: 0.5 = 0,5% ao mês)
    private static final Map<String, BigDecimal> TAXA_INFLACAO_MENSAL = new HashMap<>();
    
    static {
        // Taxas mensais aproximadas do IPCA (exemplo - em produção, buscar de API do IBGE)
        // Formato: "YYYY-MM" -> taxa mensal em decimal
        TAXA_INFLACAO_MENSAL.put("2024-01", new BigDecimal("0.0042")); // 0,42%
        TAXA_INFLACAO_MENSAL.put("2024-02", new BigDecimal("0.0041")); // 0,41%
        TAXA_INFLACAO_MENSAL.put("2024-03", new BigDecimal("0.0016")); // 0,16%
        TAXA_INFLACAO_MENSAL.put("2024-04", new BigDecimal("0.0038")); // 0,38%
        TAXA_INFLACAO_MENSAL.put("2024-05", new BigDecimal("0.0044")); // 0,44%
        TAXA_INFLACAO_MENSAL.put("2024-06", new BigDecimal("0.0021")); // 0,21%
        TAXA_INFLACAO_MENSAL.put("2024-07", new BigDecimal("0.0017")); // 0,17%
        TAXA_INFLACAO_MENSAL.put("2024-08", new BigDecimal("0.0024")); // 0,24%
        TAXA_INFLACAO_MENSAL.put("2024-09", new BigDecimal("0.0026")); // 0,26%
        TAXA_INFLACAO_MENSAL.put("2024-10", new BigDecimal("0.0021")); // 0,21%
        TAXA_INFLACAO_MENSAL.put("2024-11", new BigDecimal("0.0025")); // 0,25%
        TAXA_INFLACAO_MENSAL.put("2024-12", new BigDecimal("0.0030")); // 0,30%
        
        // Taxa média histórica do IPCA (usada como fallback): ~0,5% ao mês = 6,17% ao ano
        TAXA_INFLACAO_MENSAL.put("MEDIA", new BigDecimal("0.005")); // 0,5% ao mês
    }

    /**
     * Calcula a inflação acumulada entre duas datas
     * @param dataInicial Data inicial
     * @param dataFinal Data final
     * @return Taxa de inflação acumulada (em decimal, ex: 0.10 = 10%)
     */
    public BigDecimal calcularInflacaoAcumulada(LocalDate dataInicial, LocalDate dataFinal) {
        if (dataInicial.isAfter(dataFinal)) {
            throw new IllegalArgumentException("Data inicial deve ser anterior à data final");
        }

        BigDecimal fatorAcumulado = BigDecimal.ONE;
        LocalDate dataAtual = dataInicial;

        while (!dataAtual.isAfter(dataFinal)) {
            String chave = dataAtual.getYear() + "-" + String.format("%02d", dataAtual.getMonthValue());
            BigDecimal taxaMensal = TAXA_INFLACAO_MENSAL.getOrDefault(chave, TAXA_INFLACAO_MENSAL.get("MEDIA"));
            
            // Fator = 1 + taxa
            BigDecimal fator = BigDecimal.ONE.add(taxaMensal);
            fatorAcumulado = fatorAcumulado.multiply(fator);
            
            // Avança para o próximo mês
            dataAtual = dataAtual.plusMonths(1).withDayOfMonth(1);
        }

        // Retorna a inflação acumulada (fator - 1)
        return fatorAcumulado.subtract(BigDecimal.ONE);
    }

    /**
     * Calcula quanto um valor atual correspondia em uma data passada (deflaciona)
     * @param valorAtual Valor atual
     * @param dataAtual Data do valor atual
     * @param dataPassada Data de referência (para a qual queremos deflacionar)
     * @return Valor deflacionado (quanto valia na data passada)
     */
    public BigDecimal calcularValorDeflacionado(BigDecimal valorAtual, LocalDate dataAtual, LocalDate dataPassada) {
        if (dataPassada.isAfter(dataAtual)) {
            throw new IllegalArgumentException("Data passada deve ser anterior à data atual");
        }

        BigDecimal inflacaoAcumulada = calcularInflacaoAcumulada(dataPassada, dataAtual);
        BigDecimal fatorInflacao = BigDecimal.ONE.add(inflacaoAcumulada);
        
        // Valor deflacionado = valor atual / fator de inflação
        return valorAtual.divide(fatorInflacao, 2, RoundingMode.HALF_UP);
    }

    /**
     * Calcula quanto um valor passado corresponderia hoje (inflaciona)
     * @param valorPassado Valor na data passada
     * @param dataPassada Data do valor passado
     * @param dataAtual Data atual (ou futura)
     * @return Valor inflacionado (quanto valeria hoje)
     */
    public BigDecimal calcularValorInflacionado(BigDecimal valorPassado, LocalDate dataPassado, LocalDate dataAtual) {
        if (dataPassado.isAfter(dataAtual)) {
            throw new IllegalArgumentException("Data passada deve ser anterior à data atual");
        }

        BigDecimal inflacaoAcumulada = calcularInflacaoAcumulada(dataPassado, dataAtual);
        BigDecimal fatorInflacao = BigDecimal.ONE.add(inflacaoAcumulada);
        
        // Valor inflacionado = valor passado * fator de inflação
        return valorPassado.multiply(fatorInflacao).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Calcula o ganho real (ganho nominal descontado da inflação)
     * @param valorInicial Valor inicial investido
     * @param valorFinal Valor final obtido
     * @param dataInicial Data do investimento inicial
     * @param dataFinal Data do valor final
     * @return Ganho real (em decimal, ex: 0.05 = 5% de ganho real)
     */
    public BigDecimal calcularGanhoReal(BigDecimal valorInicial, BigDecimal valorFinal, 
                                       LocalDate dataInicial, LocalDate dataFinal) {
        // Ganho nominal
        BigDecimal ganhoNominal = valorFinal.subtract(valorInicial);
        BigDecimal ganhoNominalPercentual = BigDecimal.ZERO;
        if (valorInicial.compareTo(BigDecimal.ZERO) > 0) {
            ganhoNominalPercentual = ganhoNominal.divide(valorInicial, 4, RoundingMode.HALF_UP);
        }

        // Inflação acumulada
        BigDecimal inflacaoAcumulada = calcularInflacaoAcumulada(dataInicial, dataFinal);

        // Ganho real = (1 + ganho nominal) / (1 + inflação) - 1
        BigDecimal umMaisGanho = BigDecimal.ONE.add(ganhoNominalPercentual);
        BigDecimal umMaisInflacao = BigDecimal.ONE.add(inflacaoAcumulada);
        BigDecimal ganhoReal = umMaisGanho.divide(umMaisInflacao, 4, RoundingMode.HALF_UP)
                                          .subtract(BigDecimal.ONE);

        return ganhoReal;
    }

    /**
     * Calcula o poder de compra de um valor (quanto ele pode comprar hoje vs. no passado)
     * @param valor Valor a analisar
     * @param dataPassada Data de referência passada
     * @param dataAtual Data atual
     * @return Poder de compra (valor deflacionado / valor atual) - indica quantas vezes mais/menos pode comprar
     */
    public BigDecimal calcularPoderDeCompra(BigDecimal valor, LocalDate dataPassada, LocalDate dataAtual) {
        BigDecimal valorDeflacionado = calcularValorDeflacionado(valor, dataAtual, dataPassada);
        
        // Poder de compra = valor deflacionado / valor atual
        // Se > 1, tinha mais poder de compra no passado
        // Se < 1, tem mais poder de compra hoje
        if (valor.compareTo(BigDecimal.ZERO) > 0) {
            return valorDeflacionado.divide(valor, 4, RoundingMode.HALF_UP);
        }
        return BigDecimal.ZERO;
    }

    /**
     * Calcula a taxa de inflação anualizada aproximada
     * @param dataInicial Data inicial
     * @param dataFinal Data final
     * @return Taxa anualizada (em decimal)
     */
    public BigDecimal calcularTaxaAnualizada(LocalDate dataInicial, LocalDate dataFinal) {
        long meses = ChronoUnit.MONTHS.between(dataInicial, dataFinal);
        if (meses <= 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal inflacaoAcumulada = calcularInflacaoAcumulada(dataInicial, dataFinal);
        
        // Taxa anualizada aproximada: inflação acumulada * (12 / meses)
        // Esta é uma aproximação linear válida para períodos curtos e taxas pequenas
        BigDecimal taxaAnualizada = inflacaoAcumulada.multiply(new BigDecimal("12"))
                                                     .divide(new BigDecimal(meses), 4, RoundingMode.HALF_UP);
        
        return taxaAnualizada;
    }
}

