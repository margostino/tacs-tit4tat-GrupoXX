package com.utn.tacs.tit4tat.model;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.utn.tacs.tit4tat.objectify.Identifiable;

@Entity
//@Scope("session")
public class Usuario implements Identifiable, Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private String name;

//	private List<Ref<Item>> items = new ArrayList<Ref<Item>>();

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
//	public List<Item> getItems() {
//		return items;
//	}
//	
//	public void setItems(List<Item> items) {
//		this.items = items;
//	}
	
	public Usuario() {
	}
	
	public Usuario(String name) {
		this.setName(name);
	}

	 @Override
	 public String toString() {
	 return "Usuario [id=" + id + ", name=" + name + "]";
	 }
}