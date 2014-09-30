package com.utn.tacs.tit4tat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.utn.tacs.tit4tat.dao.SolicitudDao;
import com.utn.tacs.tit4tat.model.Solicitud;
import com.utn.tacs.tit4tat.service.SolicitudService;

@Service(value="solicitudService")
@Scope(value="prototype")
public class SolicitudServiceImpl implements SolicitudService {

	@Autowired
	private SolicitudDao solicitudDao;

	@Override
	public Solicitud saveSolicitud(Solicitud solicitud) {
		Long id = this.solicitudDao.save(solicitud);
		solicitud.setId(id);
		return solicitud;
	}

	@Override
	public void deleteSolicitud(Solicitud solicitud) {
		this.solicitudDao.delete(solicitud);
	}

	@Override
	public List<Solicitud> getSolicitudes() {
		return this.solicitudDao.findAll();
	}

	@Override
	public Solicitud getSolicitudesById(Long id) {
		return this.solicitudDao.getById(id);
	}

	@Override
	public void updateSolicitud(Solicitud solicitud) {
		this.solicitudDao.saveOrUpdate(solicitud);
	}
}