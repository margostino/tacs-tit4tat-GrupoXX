package com.utn.tacs.tit4tat.controller;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.appengine.api.datastore.Blob;
import com.utn.tacs.tit4tat.model.Item;
import com.utn.tacs.tit4tat.model.Solicitud;
import com.utn.tacs.tit4tat.model.Usuario;
import com.utn.tacs.tit4tat.objectify.Utils;
import com.utn.tacs.tit4tat.service.ItemService;
import com.utn.tacs.tit4tat.service.SolicitudService;
import com.utn.tacs.tit4tat.service.UsuarioService;

@Controller
@RequestMapping("/notifications")
public class NotificationsController {

	@Autowired
	private SolicitudService solicitudService;

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private ItemService itemService;
	
	List<Solicitud> notifications = new ArrayList<Solicitud>();
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getNotifications() {
		ModelAndView model = new ModelAndView("notifications");
		Usuario me = new Usuario("martin");
		me.setId(1l);
		//Usuario user1 = new Usuario("Martin Dagostino");
		
		this.usuarioService.saveUsuario(me);
		
		Item offeredItem = new Item();
		offeredItem.setDescription("Ipod Touch");
		offeredItem.setOwner(me);
		offeredItem.setId(2l);
		
		Item requestItem = new Item();
		requestItem.setDescription("Galaxy S5");
		requestItem.setOwner(me);
		requestItem.setId(1l);
		
		Solicitud sol = new Solicitud();
		sol.setId(99L);
		sol.setDetail("Solicitud 1");
		sol.setRequestItem(requestItem);
		sol.setOfferedItem(offeredItem);
		//
		// this.solicitudService.saveSolicitud(sol);
		//
		notifications.add(sol);
		// List<Solicitud> notifications =
		// this.solicitudService.getSolicitudesPendientes();
		//model.setViewName("notifications");
		model.addObject("notifications", notifications);
		
		
		return model;
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public @ResponseBody
	String getItemsSearch(@RequestParam(value = "id") String id,
			@RequestParam(value = "state") String state) {

		try {
			this.solicitudService.changeStateOfSolicitud(id, state);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return "La solicitud proceso correctamente: " + id;
	}
	
	@SuppressWarnings("unchecked")
	@Consumes(value ="application/json")
	@RequestMapping (method = RequestMethod.POST)
	public ModelAndView create(@RequestBody String request) {	
		
		ModelAndView model = new ModelAndView("notifications");				
		JSONObject obj=new JSONObject();
		obj.put("create","OK");		 
		model.addObject("response", obj);
		
		try{
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonRequest = (JSONObject) jsonParser.parse(request);	
			Long owner_id = Long.valueOf(jsonRequest.get("owner_id").toString());
			Long item_id = Long.valueOf(jsonRequest.get("item_id").toString());
			Long user_id = Long.valueOf(jsonRequest.get("user_id").toString());
			Long user_item_id = Long.valueOf(jsonRequest.get("user_item_id").toString());
			
			Item offeredItem = this.itemService.getItemsById(user_item_id);
			Item requestedItem = this.itemService.getItemsById(item_id);
			
			Usuario offeredUser = this.usuarioService.getUsuariosById(user_id);
			Usuario requestedUser = this.usuarioService.getUsuariosById(owner_id);
			
			Solicitud sol = new Solicitud();
			sol.setOfferedItem(offeredItem);
			sol.setOfferedUser(offeredUser);
			
			sol.setRequestItem(requestedItem);
			sol.setRequestUser(requestedUser);
			
			sol.setState(0);
			
			this.solicitudService.saveSolicitud(sol);
		
		}catch(Exception e){
			System.out.println(e.toString());
		}
		
	
		return model;
	}
		
//	@RequestMapping(value="/create", method = RequestMethod.POST)
//	public  @ResponseBody String  createn(@RequestParam(value = "json") String jsonRequest) {
// 
//		return "redirect:create";
//	}
	
	
//	@SuppressWarnings("unchecked")
//	@Consumes(value ="application/json")
//	@RequestMapping (value="/{idNotifications}/reply", method = RequestMethod.PUT)
//	public @ResponseBody ModelAndView update(@PathVariable("idNotifications") String id, @RequestBody String jsonRequest) {	
//		
//		ModelAndView model = new ModelAndView("edit");				
//		JSONObject obj=new JSONObject();
//		obj.put("update","OK");		 
//		model.addObject("response", obj);
//		
//        return model;
//    }	
	
	@RequestMapping(value="/create/{id}", method =RequestMethod.GET)
	//public  @ResponseBody ModelAndView  createGet(@RequestParam(value = "json") String jsonRequest) { 
	public  ModelAndView  createGet() { 
		Item item = new Item();
		Solicitud solicitud = new Solicitud();
		ModelAndView model = new ModelAndView("notifications/create");		
		try {
			solicitud.setId(1L);
			solicitud.setState(1);

		//model.addObject("solicitud", solicitud);		
		
		Solicitud notification = new Solicitud();
		notification.setId(1L);
		notification.setDetail("mydetails");
		
		Item itemOfrecido = new Item();
		itemOfrecido.setId(1L);
		itemOfrecido.setDescription("IPod 32GB");
		itemOfrecido.setPermalink("http://mercadolibre.com.ar/item/ml12312");
		String[] category = {"Electronica"};
		itemOfrecido.setCategory(category);
		notification.setOfferedItem(itemOfrecido);
		
		
		Item itemSolicitado = new Item();
		itemSolicitado.setId(1L);
		itemSolicitado.setDescription("Bike");
		itemSolicitado.setPermalink("http://mercadolibre.com.ar/item/ml12312");
		String[] category2 = {"NO SE"};
		item.setCategory(category2);

		
		notification.setRequestItem(itemSolicitado);
		model.addObject("notification", notification);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	

		
		//model.setViewName("notifications/create");
		return model;
	}
	
	@SuppressWarnings("unchecked")
	@Consumes(value ="application/json")
	@RequestMapping (value = "/{notId}/share", method = RequestMethod.POST)
    //public @ResponseBody ModelAndView create(@ModelAttribute("item") Item item, BindingResult result) {
	public @ResponseBody ModelAndView share(@PathVariable("notId") String itemId, @RequestBody String jsonRequest) {	
		
		ModelAndView model = new ModelAndView("notifications/share");				
		JSONObject obj=new JSONObject();
		obj.put("share","OK");		 
		model.addObject("response", obj);
		
        return model;
    }
		
//	@RequestMapping(value = "/edit/{notId}", method = RequestMethod.PUT)
//	public String edit(@PathVariable("notId") String notId) {
//		
//		return "notifications/edit";
//	}
	
//	@RequestMapping(value = "/delete/{notId}", method = RequestMethod.DELETE)
//	public String delete(@PathVariable("notId") String notId) {
//		
//		return "notifications/delete";
//	}
//	
//	@RequestMapping(value = "/{notId}/accepted", method = RequestMethod.PUT)
//	public String accepted(@PathVariable("notId") String notId) {
//		
//		return "notifications/accepted";
//	}	
//	
//	@RequestMapping(value = "/{notId}/rejected", method = RequestMethod.PUT)
//	public String rejected(@PathVariable("notId") String notId) {
//		
//		return "notifications/rejected";
//	}		
}
