package in.ashokit.serviceimp;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import in.ashokit.dto.QuoteApiResponseDTO;
import in.ashokit.service.DashboardService;
@Service
public class DashboardServiceImpl implements DashboardService {
	private String quoteApiURL="https://dummyjson.com/quotes/random";

	@Override
	public QuoteApiResponseDTO getQuote() {
	RestTemplate rt=new RestTemplate();
	// send http get req and store response into quote ApiResponseDTO Object
	
	ResponseEntity<QuoteApiResponseDTO> forEntity=rt.getForEntity(quoteApiURL,QuoteApiResponseDTO.class);
	
	QuoteApiResponseDTO body=forEntity.getBody();
		return body ;
	}

}
