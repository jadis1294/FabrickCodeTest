package com.exercise.fabrick.demo.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.exercise.fabrick.demo.exception.FabrickException;
import com.exercise.fabrick.demo.exception.RecordNotFoundException;
import com.exercise.fabrick.demo.model.request.Account;
import com.exercise.fabrick.demo.model.request.Creditor;
import com.exercise.fabrick.demo.model.request.InviaBonificoRequest;
import com.exercise.fabrick.demo.model.response.ContoCorrenteResponse;
import com.exercise.fabrick.demo.model.response.InviaBonificoResponse;
import com.exercise.fabrick.demo.model.response.ListaTransazioniResponse;
import com.exercise.fabrick.demo.model.response.ResponseResource;
import com.exercise.fabrick.demo.model.response.Transazione;
import com.exercise.fabrick.demo.persistence.entity.Transazioni;
import com.exercise.fabrick.demo.persistence.repository.TransazioniRepository;
import com.exercise.fabrick.demo.service.caller.ContoCorrenteServiceCaller;

import okhttp3.mockwebserver.MockWebServer;

@ExtendWith(MockitoExtension.class)
public class ContoCorrenteServiceTest {
    public static MockWebServer mockWebServer;
    @InjectMocks
	ContoCorrenteServiceImpl service;
    @Mock
	private ContoCorrenteServiceCaller caller;
    @Mock
	TransazioniRepository transazioniRepository;

    @Test
	public void getSaldoContoCorrenteTest()  {
        assertThrows(RecordNotFoundException.class,() -> service.getSaldoContoCorrente("121324", "test", "test"));
        lenient().when(caller.getSaldo(any(),any(),any())).thenReturn(null);
		assertThrows(RecordNotFoundException.class,() -> service.getSaldoContoCorrente("121324", "test", "test"));
        lenient().when(caller.getSaldo(any(),any(),any())).thenReturn(new ContoCorrenteResponse(ResponseResource.RETCODE_OPERAZIONE_CONCLUSA_CON_SUCCESSO));
        assertDoesNotThrow(() -> service.getSaldoContoCorrente("121324", "test", "test"));

	}

    @Test
	public void invioBonificoTest()  {
        InviaBonificoRequest request= new InviaBonificoRequest();
        assertThrows(FabrickException.class, () -> service.invioBonifico("121324", "test", "test",request));
        request.setAmount("1234");
        Creditor creditor= new Creditor();
        Account account= new Account();
        account.setAccountCode("accountCode");
        creditor.setAccount(account);
        creditor.setName("name");
        request.setCreditor(creditor);
        request.setCurrency("currency");
        request.setDescription("description");
        request.setExecutionDate("data sbagliata");
        assertThrows(FabrickException.class, () -> service.invioBonifico("121324", "test", "test",request));
        request.setExecutionDate("2024-04-10");
        assertThrows(FabrickException.class, () -> service.invioBonifico("121324", "test", "test",request));
        lenient().when(caller.invioBonifico(any(),any(),any(),any())).thenReturn(null);
        assertThrows(FabrickException.class, () -> service.invioBonifico("121324", "test", "test",request));
        lenient().when(caller.invioBonifico(any(),any(),any(),any())).thenReturn(new InviaBonificoResponse(ResponseResource.RETCODE_OPERAZIONE_CONCLUSA_CON_SUCCESSO));
        assertDoesNotThrow(() -> service.invioBonifico("121324", "test", "test",request));
        
    }

    @Test
	public void getListaTransazioniTest()  {
        lenient().when(caller.getListaTransizioni(any(),any(),any(),any(),any())).thenReturn(new ListaTransazioniResponse(ResponseResource.RETCODE_OPERAZIONE_CONCLUSA_CON_SUCCESSO));
        assertThrows(FabrickException.class, () -> service.getListaTransizioni("121324",null,null, "test", "test"));
        assertThrows(FabrickException.class, () -> service.getListaTransizioni("121324","data sbagliata","data sbagliata", "test", "test"));
        assertThrows(FabrickException.class, () -> service.getListaTransizioni("121324","data sbagliata","2024-04-10", "test", "test"));
        lenient().when(transazioniRepository.save(any())).thenReturn(new Transazioni(null, null, null, null, 0, null, null));
        assertDoesNotThrow(() -> service.getListaTransizioni("121324","2024-04-10","2024-04-10", "test", "test"));
        ListaTransazioniResponse res= new ListaTransazioniResponse(ResponseResource.RETCODE_OPERAZIONE_CONCLUSA_CON_SUCCESSO);
        List<Transazione> list= new ArrayList<>();
        Transazione t= new Transazione();
        list.add(t);
        res.setList(list);
        lenient().when(caller.getListaTransizioni(any(),any(),any(),any(),any())).thenReturn(res);
        assertDoesNotThrow( () -> service.getListaTransizioni("121324","2024-04-10","2024-04-10", "test", "test"));
    }

}
