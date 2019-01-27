package com.kidscademy.tool;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.kidscademy.atlas.Instrument;
import com.kidscademy.dao.AtlasDao;
import com.kidscademy.dao.AtlasDaoImpl;

import js.json.Json;
import js.lang.ConfigException;
import js.transaction.TransactionFactory;
import js.transaction.eclipselink.TransactionFactoryImpl;
import js.util.Classes;
import js.util.Strings;

public class ExportInstrument {
    public static void main(String... args) throws IOException, ConfigException {
	Json json = Classes.loadService(Json.class);

	TransactionFactory factory = new TransactionFactoryImpl("import");
	AtlasDao dao = factory.newInstance(AtlasDaoImpl.class);

	ZipOutputStream zip = new ZipOutputStream(new FileOutputStream("d://tmp/instruments.zip"));

	List<Instrument> instruments = dao.findObjectByType(Instrument.class);

	for (Instrument instrument : instruments) {
	    ZipEntry entry = new ZipEntry(Strings.concat("collection/", instrument.getName(), "/instrument.json"));
	    zip.putNextEntry(entry);
	    zip.write(json.stringify(instrument).getBytes("UTF-8"));
	    zip.closeEntry();
	}

	zip.close();
    }
}
