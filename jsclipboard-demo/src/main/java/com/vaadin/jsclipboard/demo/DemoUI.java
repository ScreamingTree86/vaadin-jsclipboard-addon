package com.vaadin.jsclipboard.demo;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.jsclipboard.ClipboardButton;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;

import javax.servlet.annotation.WebServlet;

@Theme("demo")
@Title("JSClipboard Add-on Demo")
@SuppressWarnings("serial")
public class DemoUI extends UI {

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = DemoUI.class, widgetset = "com.vaadin.jsclipboard.demo.DemoWidgetSet")
	public static class Servlet extends VaadinServlet {
	}

	@Override
	protected void init(VaadinRequest request) {
		final HorizontalLayout layout = new HorizontalLayout();
		layout.setStyleName("demoContentLayout");
		layout.setSizeFull();
		layout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

		createNewWayCopyToClipboard(layout);

		setContent(layout);
	}

	private void createNewWayCopyToClipboard(HorizontalLayout layout) {

		Label label = new Label(
				"This variant was done using the library <a href='https://clipboardjs.com/' >clipboard.js</a>");
		label.setContentMode(ContentMode.HTML);

		final TextArea anotherArea = new TextArea();
		anotherArea.setId("clipboardTarget");
		anotherArea.setValue("Another example to copy to clipboard");

		ClipboardButton clipboardButton = new ClipboardButton("clipboardTarget");
		clipboardButton.addSuccessListener(new ClipboardButton.SuccessListener() {

			@Override
			public void onSuccess() {
				Notification.show("Copy to clipboard successful");
			}
		});
		clipboardButton.addErrorListener(new ClipboardButton.ErrorListener() {

			@Override
			public void onError() {
				Notification.show("Copy to clipboard unsuccessful", Notification.Type.ERROR_MESSAGE);
			}
		});

		Button source = new Button(FontAwesome.CODE);
		source.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(Button.ClickEvent event) {
				Window win = createSourceWindow();
				win.setModal(true);
				UI.getCurrent().addWindow(win);
			}
		});

		VerticalLayout wrapper = new VerticalLayout();
		wrapper.setSpacing(true);
		wrapper.setDefaultComponentAlignment(Alignment.TOP_CENTER);
		wrapper.setWidth("100%");
		wrapper.addComponents(new HorizontalLayout(label), anotherArea, clipboardButton, source);
		layout.addComponent(wrapper);
	}

	private Window createSourceWindow() {
		Window sourceWindow = new Window("Example");
		VerticalLayout content = new VerticalLayout();
		content.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		Label sourceCode = new Label("<pre><code>" + "        final TextArea anotherArea = new TextArea();\n"
				+ "        anotherArea.setId(\"clipboardTarget\");\n"
				+ "        anotherArea.setValue(\"Another example to copy to clipboard\");\n" + "\n"
				+ "        ClipboardButton clipboardButton = new ClipboardButton(\"clipboardTarget\");\n"
				+ "        clipboardButton.addSuccessListener(new ClipboardButton.SuccessListener() {\n" + "\n"
				+ "            @Override\n" + "            public void onSuccess() {\n"
				+ "                Notification.show(\"Copy to clipboard successful\");\n" + "            }\n"
				+ "        });\n" + "        clipboardButton.addErrorListener(new ClipboardButton.ErrorListener() {\n"
				+ "\n" + "            @Override\n" + "            public void onError() {\n"
				+ "                Notification.show(\"Copy to clipboard unsuccessful\", Notification.Type.ERROR_MESSAGE);\n"
				+ "            }\n" + "        });</code></pre>", ContentMode.HTML);

		content.addComponent(new HorizontalLayout(sourceCode));
		sourceWindow.setContent(content);
		sourceWindow.setHeight("400px");
		return sourceWindow;
	}

}
