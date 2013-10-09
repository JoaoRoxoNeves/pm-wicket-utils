package com.premiumminds.webapp.wicket.test.bootstrap;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Locale;

import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.util.tester.TagTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Test;

import com.premiumminds.webapp.wicket.bootstrap.BootstrapDatepicker;
import com.premiumminds.webapp.wicket.bootstrap.SpecialDate;

public class BootstrapDatepickerTest {
	
	private BootstrapDatepicker component;
	private DateTextField field;

	@Test
	public void testRender() {
		WicketTester tester = createTester(true);
		
		TagTester tag = tester.getTagByWicketId("datepicker");
		
		assertTrue(tag.hasAttribute("data-date-format"));
		assertEquals(field.getTextFormat().toLowerCase(), tag.getAttribute("data-date-format"));
	}
	
	@Test
	public void testLanguage(){
		WicketTester tester = createTester(false);
		
		TagTester tag = tester.getTagByWicketId("datepicker");
		
		assertTrue(tag.hasAttribute("data-date-language"));
		assertEquals("fr", tag.getAttribute("data-date-language"));
		
		// would be cool to test if js file was also included
	}
	
	
	private WicketTester createTester(boolean enabled){
		WicketTester tester = new WicketTester(new WebApplication() {
			
			@Override
			public Class<? extends Page> getHomePage() {
				return null;
			}
			
			@Override
			public Session newSession(Request request, Response response) {
				Session session = super.newSession(request, response);
				session.setLocale(Locale.FRENCH);
				return session;
			}
		}){
			@Override
			protected String createPageMarkup(String componentId) {
				return "<div class=\"date\" wicket:id=\"datepicker\">"+
						"	<input size=\"16\" type=\"text\" class=\"input-small\" wicket:id=\"input\">"+
						"	<span class=\"add-on\"><i class=\"icon-calendar\"></i></span>"+
						"</div>";
			}
		};
		
		component = new BootstrapDatepicker("datepicker") {
			private static final long serialVersionUID = 1L;

			@Override
			public List<SpecialDate> getSpecialDates() {
				return null;
			}
			
		};
		
		component.add(field = new DateTextField("input"));
		
		tester.startComponentInPage(component);
		return tester;
	}
}
