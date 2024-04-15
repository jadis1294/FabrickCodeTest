# Fabrick Code Test
Project for fabrick  Interview's test 

Avuto problemi con il richiamo delle API 
nelle giornate dal 10 al 15 aprile.

Scritto al supporto tecnico per risoluzione in data 10/04.
Aperto il ticket FCU-1370 (al momento della consegna ancora risulta esser aperto).

La problematica è relativa al fatto che al richiamo dei servizi viene risposto con un Forbidden 403 
restituendo il seguente errore nonostante siano state impostate come da manuale Api-Key e Auth-Schema:

{
    "status": "KO",
    "errors": [
        {
            "code": "GTW900",
            "description": "Generic Error",
            "params": ""
        }
    ],
    "payload": {}
}

ticket Aperto:
https://fabrick-platform.atlassian.net/servicedesk/customer/portal/6/FCU-1370
