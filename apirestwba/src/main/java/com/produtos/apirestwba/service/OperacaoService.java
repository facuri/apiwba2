package com.produtos.apirestwba.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.produtos.apirestwba.model.DataDTO;
import com.produtos.apirestwba.model.Operacao;
import com.produtos.apirestwba.model.Titulo;
import com.produtos.apirestwba.repository.OperacaoRepository;
import com.produtos.apirestwba.repository.TituloRepository;

@Service
public class OperacaoService {
	
	@Autowired
	OperacaoRepository operacaoRepository;
	
	@Autowired
	TituloRepository tituloRepository;
	
	public List<Operacao> listAll(){
		
	    return operacaoRepository.findAll();
	 
	}

  public Operacao getOp(Long id) {
	 
	   return operacaoRepository.findById(id).get();
 }
 
  public Operacao saveOp(Operacao operacao) {
	 
	  for(int pos = 0; pos < operacao.getTitulos().size(); pos++) {
			operacao.getTitulos().get(pos).setOperacao(operacao);
		 }
	  return  operacaoRepository.save(operacao);
	 
 }
 
  public Operacao updateOp(Operacao operacao) {
	 
	 for(int pos = 0; pos < operacao.getTitulos().size(); pos++) {
		 
			operacao.getTitulos().get(pos).setOperacao(operacao);
			
		}
	 return  operacaoRepository.save(operacao);
 

 }
 
 public void deleteOp(Long id) {
	 
	 operacaoRepository.deleteById(id);
  }
 
 public Operacao salvarOperacaoSom(Operacao operacao, Titulo titulo) {
	 
	   for(int pos = 0; pos < operacao.getTitulos().size(); pos++) {
		  
		   operacao.getTitulos().get(pos).setOperacao(operacao);
		
		   if(operacao.getValorTotal() != null && titulo.getValor() != null)  
	           //Soma dos Valores
		       operacao.setValorTotal(operacao.getValorTotal().add(titulo.getValor()));
			   
	}
	 
	return operacaoRepository.save(operacao);
	 
}	
 
  public ResponseEntity<DataDTO>dataDTOOp(Long id, Long idTi){
	  
	   Optional<Operacao> operacao = operacaoRepository.findById(id);
	  
	   Optional<Titulo> titulo = tituloRepository.findById(idTi);
	   
	  return new ResponseEntity<DataDTO>(new DataDTO(operacao.get(), titulo.get()), HttpStatus.OK);
  
	  }
  
    
  
}
