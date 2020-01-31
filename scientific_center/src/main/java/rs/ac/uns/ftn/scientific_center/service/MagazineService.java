package rs.ac.uns.ftn.scientific_center.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import rs.ac.uns.ftn.scientific_center.dto.CompletePaymentDTO;
import rs.ac.uns.ftn.scientific_center.dto.MagazineDTO;
import rs.ac.uns.ftn.scientific_center.dto.request.AgreementRequest;
import rs.ac.uns.ftn.scientific_center.dto.request.PaymentOrderRequest;
import rs.ac.uns.ftn.scientific_center.dto.request.SubscribeRequest;
import rs.ac.uns.ftn.scientific_center.dto.response.PaymentOrderResponse;
import rs.ac.uns.ftn.scientific_center.dto.response.SimpleResponse;
import rs.ac.uns.ftn.scientific_center.exception.NotFoundException;
import rs.ac.uns.ftn.scientific_center.mapper.MagazineMapper;
import rs.ac.uns.ftn.scientific_center.model.AgreementTransaction;
import rs.ac.uns.ftn.scientific_center.model.Magazine;
import rs.ac.uns.ftn.scientific_center.model.Membership;
import rs.ac.uns.ftn.scientific_center.model.PricelistItem;
import rs.ac.uns.ftn.scientific_center.model.RoleName;
import rs.ac.uns.ftn.scientific_center.model.ShoppingCart;
import rs.ac.uns.ftn.scientific_center.model.SubscriptionType;
import rs.ac.uns.ftn.scientific_center.model.Transaction;
import rs.ac.uns.ftn.scientific_center.model.TransactionStatus;
import rs.ac.uns.ftn.scientific_center.repository.AgreementTransactionRepository;
import rs.ac.uns.ftn.scientific_center.repository.MagazineRepository;
import rs.ac.uns.ftn.scientific_center.repository.MembershipRepository;
import rs.ac.uns.ftn.scientific_center.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import javax.print.attribute.UnmodifiableSetException;

@Service
public class MagazineService {

    @Autowired
    private MagazineRepository magazineRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private MembershipRepository membershipRepository;

    @Autowired
    private MagazineMapper magazineMapper;
    
    @Autowired
    private AgreementTransactionRepository agreementTransactionRepository;
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Value("${subscribe.agreement.url}")
    private String agreementUrl;
    
    @Value("${payment.callbackAgreement.url}")
    private String calbackUrl;

    public List<MagazineDTO> getAllMagazines(){
        List<Magazine> magazines = magazineRepository.findAll();
        return magazineMapper.magazinesToMagazineDTOs(magazines);
    }

    public MagazineDTO getMagazine(Long magazineId){
        Magazine magazine = magazineRepository.findById(magazineId).orElseThrow(NotFoundException::new);
        return magazineMapper.magazineToMagazineDTO(magazine);
    }
    
    public PaymentOrderResponse subscribe(SubscribeRequest subscribeRequest) {

    	AgreementTransaction agreementTransaction = new AgreementTransaction();
    	agreementTransaction.setOrderId(UUID.randomUUID().toString());
    	agreementTransaction.setStatus(TransactionStatus.CREATED);
    	agreementTransaction.setTimes(LocalDateTime.now());
    	agreementTransaction.setMagazineId(subscribeRequest.getMagazineId());
    	agreementTransaction.setMagazineIssn(subscribeRequest.getMagazineIssn());
    	agreementTransactionRepository.save(agreementTransaction);
    	
    	User user = (User) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    	Collection<GrantedAuthority> authorities = user.getAuthorities();
    	System.out.println("AAAA");
    	
    	Membership membership = membershipRepository.findByMagazineIdAndSubscriptionType(subscribeRequest.getMagazineId(), SubscriptionType.READER_PAYS);
    	
    	String subscriber = null;
    	for(GrantedAuthority authority: authorities) {
    		System.out.println(authority.getAuthority());
    		if(authority.getAuthority().equals(RoleName.AUTHOR.toString())) {
    			subscriber = "Autors";
    			break;
    		}
    		else if(authority.getAuthority().equals(RoleName.READER.toString())) {
    			subscriber = "Readers";
    			break;
    		}
    	}
    	
    	if(membership == null)	{
    		System.out.println("Membership null");
    		return null;
    	}
    	
    	if(subscriber == null)	{
    		System.out.println("subscriber null");
    		return null;
    	}
    	
    	
        return restTemplate.postForObject(agreementUrl, new AgreementRequest(subscriber,membership.getUser().getEmail(),agreementTransaction.getOrderId(),calbackUrl), PaymentOrderResponse.class);
    }
    
    public SimpleResponse completePayment(CompletePaymentDTO completePaymentDTO){
    	AgreementTransaction transaction = agreementTransactionRepository.findByOrderId(completePaymentDTO.getTransactionId());
        transaction.setStatus(completePaymentDTO.getTransactionStatus());
        agreementTransactionRepository.save(transaction);

        /*if(transaction.getTransactionStatus() == TransactionStatus.SUCCESS){
            if(transaction.getSubscriptionType() == SubscriptionType.OPEN_ACCESS){
                changeArticleMembership(transaction.getCustomer().getShoppingCart());
            }

            transaction.getCustomer().getShoppingCart().getItems().clear();
            shoppingCartRepository.save(transaction.getCustomer().getShoppingCart());
        }*/

        return new SimpleResponse("Success.");
    }
}
