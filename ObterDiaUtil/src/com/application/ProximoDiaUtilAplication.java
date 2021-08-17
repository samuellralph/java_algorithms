package com.application;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.application.enums.SemanaEnum;
import com.application.util.DateUtils;

public class ProximoDiaUtilAplication {

	public static void main(String[] args) throws Exception {
		ProximoDiaUtilAplication t = new ProximoDiaUtilAplication();
		/**
		 * Este método apenas obtem o próximo dia util de uma data de operação informada
		 */
		t.testeChamadaSemJanelaOperacao();

		/**
		 * Este método obtem o próximo dia util de uma janela de operacao (dia da
		 * semana) a partir de uma data data de operação informada
		 */
		t.testeChamadaComJanelaOperacao();
	}

	private void testeChamadaSemJanelaOperacao() throws ParseException, Exception {

		// Data em que a operacao vai ser executada.
		Date dataOperacao = converteStringToDate("13/08/2021");

		/*
		 * Horario limite em que a operacao deve ser executada, apos esse horario joga
		 * para o proximo dia util
		 */
		String horarioLimite = "11:00";

		List<Date> listaFeriados = new ArrayList<>();
		Date feriado1 = DateUtils.converterStringToDate("01/08/2021", DateUtils.FORMAT_DATE);
		listaFeriados.add(feriado1);

		Date novaDataOperacao = obterProximoDiaUtil(dataOperacao, listaFeriados, horarioLimite);
		System.out.println("#### Método que obtem próximo dia Útil - Sem janela de operação ####");
		System.out.print("Próximo dia Útil: ");
		System.out.print(converteDateToString(novaDataOperacao));
		System.out.print(" - ");
		System.out.print(SemanaEnum.getSemanaEnum(DateUtils.obterDiaSemana(novaDataOperacao)).getDescricao());
		System.out.print(System.lineSeparator() + System.lineSeparator());
	}

	private void testeChamadaComJanelaOperacao() throws ParseException, Exception {
		List<Date> listaFeriados = new ArrayList<>();
		Date feriado1 = DateUtils.converterStringToDate("12/08/2021", DateUtils.FORMAT_DATE);
		listaFeriados.add(feriado1);

		/*
		 * Data em que a operacao vai ser executada, se for informada uma data que nao
		 * seja do dia da janela de operacao (ex:quinta) vai data vai ser remanejada
		 * para a proxima quinta.
		 */
		Date dataOperacao = converteStringToDate("12/08/2021");

		/*
		 * Horario limite em que a operacao deve ser executada, apos esse horario joga
		 * para o proximo dia util
		 */
		String horarioLimite = "11:00";

		/*
		 * Janela de operacao (Dia da semana que é permitido a execução), se for
		 * informado sexta, vai ser executado somente nas sextas-feiras.
		 */
		SemanaEnum janelaOperacao = SemanaEnum.QUINTA;

		Date novaDataOperacao = obterProximaJanelaOperaca(dataOperacao, janelaOperacao, listaFeriados, horarioLimite);

		System.out.println("#### Método que obtem próximo dia Útil - Com janela de operação ####");
		System.out.print("Próximo dia Útil: ");
		System.out.print(converteDateToString(novaDataOperacao));
		System.out.print(" - ");
		System.out.println(SemanaEnum.getSemanaEnum(DateUtils.obterDiaSemana(novaDataOperacao)).getDescricao());
	}

	protected Date obterProximaJanelaOperaca(Date dataOperacao, SemanaEnum diaJanelaOperacao, List<Date> feriados,
			String horaLimite) throws Exception {
		try {
			if (dataOperacao == null || diaJanelaOperacao == null || isEmpty(horaLimite)) {
				throw new NullPointerException();
			}

			boolean isFeriado = DateUtils.isFeriado(dataOperacao, feriados);
			boolean isHojeForaHorarioLimiteFundo = isDataEmitidaForaHorarioLimiteFundo(horaLimite, dataOperacao);
			boolean isFimSemana = DateUtils.isFimSemana(dataOperacao);

			if (!isDiaJanelaOperacao(dataOperacao, diaJanelaOperacao) || isFeriado || isHojeForaHorarioLimiteFundo
					|| isFimSemana) {
				Date proximaData = DateUtils.adicionarDia(dataOperacao, DateUtils.INTEGER_ONE);
				return obterProximaJanelaOperaca(proximaData, diaJanelaOperacao, feriados, horaLimite);
			}
			return dataOperacao;
		} catch (NullPointerException | ParseException e) {
			String message = "Falha ao obter a data da Janela de Operação.";
			throw new Exception(message, e);
		}
	}

	protected Date obterProximoDiaUtil(Date data, List<Date> feriados, String horaAplicacao) throws Exception {
		try {
			if (data == null || isEmpty(horaAplicacao)) {
				throw new NullPointerException();
			}

			boolean isHojeForaHorarioLimiteFundo = isDataEmitidaForaHorarioLimiteFundo(horaAplicacao, data);
			boolean isFeriado = DateUtils.isFeriado(data, feriados);
			boolean isFimSemana = DateUtils.isFimSemana(data);

			if (isFimSemana || isFeriado || isHojeForaHorarioLimiteFundo) {
				Date proximaData = DateUtils.adicionarDia(data, DateUtils.INTEGER_ONE);
				return obterProximoDiaUtil(proximaData, feriados, horaAplicacao);
			}
			return data;
		} catch (NullPointerException | ParseException e) {
			String message = "Falha ao obter a data do dia útil.";
			throw new Exception(message, e);
		}
	}

	protected Boolean isDataEmitidaForaHorarioLimiteFundo(String dataHoraLimite, Date dataOperacao)
			throws ParseException {
		Date data = DateUtils.converterStringToDate(dataHoraLimite, DateUtils.FORMAT_HHMM);
		Date dataHora = DateUtils.montarHoraHoje(data).getTime();
		return DateUtils.dataIgualHoje(dataOperacao) && DateUtils.isDataAnteriorAtual(dataHora);
	}

	private boolean isDiaJanelaOperacao(Date dataOperacao, SemanaEnum diaJanelaOperacao) {
		return SemanaEnum.getSemanaEnum(DateUtils.obterDiaSemana(dataOperacao)).equals(diaJanelaOperacao);
	}

	protected boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	private Date converteStringToDate(String dataOperacao) throws ParseException {
		return DateUtils.converterStringToDate(dataOperacao, DateUtils.FORMAT_DATE);
	}

	private String converteDateToString(Date novaDataOperacao) throws ParseException {
		return DateUtils.formatarDataToString(novaDataOperacao, "dd/MM/yyyy");
	}
}
