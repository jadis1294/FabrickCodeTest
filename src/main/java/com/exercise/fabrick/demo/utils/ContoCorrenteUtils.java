package com.exercise.fabrick.demo.utils;

import java.text.SimpleDateFormat;

public class ContoCorrenteUtils {
    //DATE 
	public static final String YYYYMMDD = "YYYY-MM-DD";
	public static SimpleDateFormat formatter = new SimpleDateFormat(YYYYMMDD);

    //ERRORI
    public static final String ERRORE_RECUPERO_TRANSAZIONI="errore durante il recupero delle transazioni";
    public static final String ERRORE_RECUPERO_SALDO="errore durante il recupero del saldo del conto corrente";
    public static final String ERRORE_INVIO_BONIFICO="errore durante il salvataggio del bonifico";
    public static final String ERRORE_DATI_INPUT="Dati in input non corretti";
    public static final String ERRORE_FORMATO_DATA="Formato data non coerente";
    public static final String ERRORE_SALVATAGGIO_DB="errore durante il salvataggio della transazion a db";

    // URL
    public static final String URL_GET_LISTA_TRANSIZIONI = "/api/gbs/banking/v4.0/accounts/{accountId}/transactions";
    public static final String URL_GET_SALDO = "/api/gbs/banking/v4.0/accounts/{accountId}/balance";
    public static final String URL_GET_INVIO_BONIFICO = "/api/gbs/banking/v4.0/accounts/{accountId}/payments/money-transfers";

    //HEADERS
    public static final String AUTH_SCHEMA = "Auth-Schema";
    public static final String API_KEY = "Api-Key";
}
