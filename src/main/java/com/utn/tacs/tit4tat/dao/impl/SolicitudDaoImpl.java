package com.utn.tacs.tit4tat.dao.impl;

import static com.utn.tacs.tit4tat.objectify.OfyService.ofy;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.utn.tacs.tit4tat.dao.SolicitudDao;
import com.utn.tacs.tit4tat.model.Solicitud;

@Service("solicitudDao")
@Scope("singleton")
public class SolicitudDaoImpl extends GenericDaoImpl<Solicitud, Long> implements
		SolicitudDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Solicitud> getSolicitudesPendientes() {
		return (List<Solicitud>) ofy().load().type(Solicitud.class).filter("state", Solicitud.PENDING).list();
	}
}
