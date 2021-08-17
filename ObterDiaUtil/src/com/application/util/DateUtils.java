package com.application.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtils {
	public static final String FORMAT_HHMM = "HH:mm";
	public static final String FORMAT_DATE = "dd/MM/yyyy";
	public static final Integer INTEGER_ZERO = 0;
	public static final Integer INTEGER_ONE = 1;

	public static int obterDiaSemana(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_WEEK);
	}

	public static Date adicionarDia(Date data, int qtdeDias) {
		Calendar dataRetorno = Calendar.getInstance();
		dataRetorno.setTime(data);
		dataRetorno.add(Calendar.DATE, qtdeDias);
		return dataRetorno.getTime();
	}

	public static boolean isDataAnteriorAtual(Date data) {
		return data.before(new Date());
	}

	public static Date converterStringToDate(String data, String formato) throws ParseException {
		DateFormat df = new SimpleDateFormat(formato);
		return df.parse(data);
	}

	public static String formatarDataToString(Date data, String formato) throws ParseException {
		DateFormat df = new SimpleDateFormat(formato);
		return df.format(data);
	}

	public static Date formatarData(Date data, String formato) throws ParseException {
		DateFormat df = new SimpleDateFormat(formato);
		String dataFormatada = df.format(data);
		return df.parse(dataFormatada);
	}

	public static Boolean dataIgualHoje(Date data) {
		Date dataAtual = new Date();
		return data.equals(dataAtual);
	}

	public static Calendar montarHoraHoje(Date hora) {
		Calendar hoje = Calendar.getInstance();
		Calendar dataMontada = Calendar.getInstance();
		dataMontada.setTime(hora);
		dataMontada.set(Calendar.DAY_OF_MONTH, hoje.get(Calendar.DAY_OF_MONTH));
		dataMontada.set(Calendar.MONTH, hoje.get(Calendar.MONTH));
		dataMontada.set(Calendar.YEAR, hoje.get(Calendar.YEAR));
		return dataMontada;
	}

	public static boolean isFimSemana(Date data) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(data);
		if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
				|| calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public static Boolean isFeriado(Date data, List<Date> listaFeriados) {
		Calendar dataCompare = zerarHoraMinSeg(data);
		for (Date feriado : listaFeriados) {
			Calendar dataFeriado = zerarHoraMinSeg(feriado);
			if (dataCompare.getTime().equals(dataFeriado.getTime())) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}

	public static Date obterProximoDiaUtil(Date data, List<Date> feriados) {
		if (isFimSemana(data) || isFeriado(data, feriados)) {
			Date proximaData = DateUtils.adicionarDia(data, INTEGER_ONE);
			obterProximoDiaUtil(proximaData, feriados);
		}
		return data;
	}

	public static Calendar zerarHoraMinSeg(Calendar data) {
		data.set(Calendar.AM_PM, Calendar.AM);
		data.set(Calendar.HOUR, INTEGER_ZERO);
		data.set(Calendar.MINUTE, INTEGER_ZERO);
		data.set(Calendar.SECOND, INTEGER_ZERO);
		data.set(Calendar.MILLISECOND, INTEGER_ZERO);
		return data;
	}

	public static Calendar zerarHoraMinSeg(Date data) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(data);
		calendar.set(Calendar.AM_PM, Calendar.AM);
		calendar.set(Calendar.HOUR, INTEGER_ZERO);
		calendar.set(Calendar.MINUTE, INTEGER_ZERO);
		calendar.set(Calendar.SECOND, INTEGER_ZERO);
		calendar.set(Calendar.MILLISECOND, INTEGER_ZERO);
		return calendar;
	}

}
