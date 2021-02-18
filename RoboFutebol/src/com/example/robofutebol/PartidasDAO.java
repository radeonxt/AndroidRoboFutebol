package com.example.robofutebol;

import java.util.ArrayList;
import java.util.Vector;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.database.SQLException;

public class PartidasDAO { 
	private static final String URL = "http://ec2-18-231-189-209.sa-east-1.compute.amazonaws.com:8080/WebServiceProject/services/PartidasDAO?wsdl";
	private static final String Namespace = "http://WebServiceProject.com.br";
	private static final String LISTAR = "listar";
	
	public ArrayList<String> listar() throws SQLException {
		ArrayList<String> lista = new ArrayList<String>();
		SoapObject listarpartidas = new SoapObject(Namespace, LISTAR);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.implicitTypes = true;
		envelope.setOutputSoapObject(listarpartidas);

		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.debug = true;
			androidHttpTransport.call("urn:listar", envelope);
			@SuppressWarnings("unchecked")
			Vector<SoapObject> resposta = (Vector<SoapObject>) envelope
					.getResponse();
			
			for (SoapObject soapobject : resposta) {
				lista.add(soapobject.getProperty("nome").toString()
						+ " "
						+ soapobject.getProperty("casagols").toString()
						+ " x "
						+ soapobject.getProperty("visitantegols").toString()
						+ " - "
						+ soapobject.getProperty("tempo").toString()
								.substring(0, 2) + " min  " + '\n'
					    + " " + soapobject.getProperty("corners").toString() + "   " +
					    soapobject.getProperty("totalshots").toString() + "   " + soapobject.getProperty("defesas").toString() + '\n'
						+ soapobject.getProperty("palpite").toString());
			}
			
			return lista;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lista;
	}
}
