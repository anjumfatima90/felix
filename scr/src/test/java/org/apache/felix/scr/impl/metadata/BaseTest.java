package org.apache.felix.scr.impl.metadata;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.Iterator;
import java.util.List;

import org.apache.felix.scr.impl.MockBundle;
import org.apache.felix.scr.impl.logger.MockBundleLogger;
import org.apache.felix.scr.impl.parser.KXml2SAXParser;
import org.apache.felix.scr.impl.xml.XmlHandler;
import org.osgi.service.component.ComponentException;
import org.xmlpull.v1.XmlPullParserException;

import junit.framework.TestCase;

public class BaseTest extends TestCase {
	private MockBundleLogger logger;

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		logger = new MockBundleLogger();
	}

	private List readMetadata(final Reader reader)
			throws IOException, ComponentException, XmlPullParserException, Exception {

		try {
			final KXml2SAXParser parser = new KXml2SAXParser(reader);

			XmlHandler handler = new XmlHandler(new MockBundle(), logger, false, false);
			parser.parseXML(handler);

			return handler.getComponentMetadataList();
		} finally {
			try {
				reader.close();
			} catch (IOException ignore) {
			}
		}
	}

	protected List readMetadata(String filename)
			throws IOException, ComponentException, XmlPullParserException, Exception {
		try (BufferedReader in = new BufferedReader(
				new InputStreamReader(getClass().getResourceAsStream(filename), "UTF-8"))) {
			return readMetadata(in);
		}
	}

	protected List readMetadataFromString(final String source)
			throws IOException, ComponentException, XmlPullParserException, Exception {
		return readMetadata(new StringReader(source));
	}

	protected ReferenceMetadata getReference(final ComponentMetadata cm, final String name) {
		List rmlist = cm.getDependencies();
		for (Iterator rmi = rmlist.iterator(); rmi.hasNext();) {
			ReferenceMetadata rm = (ReferenceMetadata) rmi.next();
			if (name.equals(rm.getName())) {
				return rm;
			}
		}

		// none found
		return null;
	}

	protected PropertyMetadata getPropertyMetadata(final ComponentMetadata cm, final String name) {
		List pmlist = cm.getPropertyMetaData();
		for (Iterator pmi = pmlist.iterator(); pmi.hasNext();) {
			PropertyMetadata pm = (PropertyMetadata) pmi.next();
			if (name.equals(pm.getName())) {
				return pm;
			}
		}

		// none found
		return null;
	}

}
