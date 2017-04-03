package org.opensrp.web.rest;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

public abstract class RestResource <T>{
	@RequestMapping(method=RequestMethod.POST, consumes={MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	private T createNew(@RequestBody T entity) {
		RestUtils.verifyRequiredProperties(requiredProperties(), entity);
		return create(entity);
	}
	
	@RequestMapping(value="/{uniqueId}", method=RequestMethod.POST, consumes={MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	private T updateExisting(@PathVariable("uniqueId") String uniqueId, @RequestBody T entity) {
//TODO		RestUtils.verifyUpdatableProperties(requiredProperties(), entity);
		RestUtils.verifyRequiredProperties(requiredProperties(), entity);
		return update(entity);//TODO
	}
	
	@RequestMapping(value="/{uniqueId}", method=RequestMethod.GET)
	@ResponseBody
	private T getById(@PathVariable("uniqueId") String uniqueId){
		return getByUniqueId(uniqueId);
	}
	
	@RequestMapping(method=RequestMethod.GET)
	@ResponseBody
	private List<T> searchBy(HttpServletRequest request, @RequestParam(value="q", required=true) String query, 
			@RequestParam(value="sort", required=false) String sort,
			@RequestParam(value="limit", required=false) Integer limit, 
			@RequestParam(value="skip", required=false) Integer skip,
			@RequestParam(value="fts", required=false) Boolean fts){
		return search(request, query, sort, limit, skip, fts);
	}
	
	public abstract List<T> search(HttpServletRequest request, String query, String sort, Integer limit, Integer skip, Boolean fts) ;
	
	public abstract T getByUniqueId(String uniqueId);
	
	public abstract List<String> requiredProperties();

	public abstract T create(T entity) ;

	public abstract T update(T entity) ;

}
