package mu.codeoffice.tag;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import mu.codeoffice.entity.Case;
import mu.codeoffice.entity.CaseActivity;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class CaseActivityTag extends SimpleTagSupport {
	
	private Case caseObject;
	
	private CaseActivity activityObject;
	
	private MessageSource messageSource;
	
	@Override
	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();
		if (messageSource == null) {
			ServletContext servletContext = ((PageContext) this.getJspContext()).getServletContext();
			WebApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
			messageSource = (MessageSource) applicationContext.getBean("messageSource");
		}

		StringBuilder buffer = new StringBuilder();
		buffer.append("<div style=\"activity-item\">");
			buffer.append("<div class=\"activity-item-user\"><img src=\"img/office/" + caseObject.getProject().getIconPath() +  ".png\"/></div>");
			buffer.append("<div class=\"activity-item-content\">");
				new UserRenderer(out).renderUser(activityObject.getUser(), false, true, true, 20, 20);
				if (activityObject.getActivityObjects().size() > 1) {
					buffer.append(String.format(" %s <a href=\"enterprise/pro_%s/%s\">%s</a>", 
							getMessage("project.c_updatednfield", activityObject.getActivityObjects().size()), 
							caseObject.getProject().getCode(), caseObject.getCode(), caseObject.getCaseHeader()));
				}
				buffer.append("<div class=\"activity-item-multiple-element\">");
				buffer.append("</div>");
			buffer.append("</div>");
		buffer.append("</div>");
		out.println(buffer.toString());
	}
	
	private String getMessage(String code, Object...arguments) {
		return messageSource.getMessage(code, arguments, LocaleContextHolder.getLocale());
	}

	
	private String getMessage(String code) {
		return getMessage(code, null);
	} 

//	<ui:param name="activityObject" value="#{cc.attrs.activityObject}"/>
//	<ui:param name="singleElement" value="#{activityObject.elements[0]}"/>
//	<h:panelGroup styleClass="activity-item" layout="block">
//		<h:panelGroup styleClass="activity-item-content" layout="block">
//			<h:panelGroup styleClass="activity-item-header" layout="block">
//				<util:employee employee="#{activity.creator}" link="true" showImage="false" width="20" height="20"/>#{' '}
//				<h:panelGroup rendered="#{activityObject.elements.size() gt 1}">
//					<h:outputFormat value="#{office.case_updatednfields}">
//						<f:param value="#{activityObject.elements.size()}"/>
//					</h:outputFormat>#{' '}
//					<h:commandLink value="#{cc.attrs.caseObject.caseHeader}" action="#{caseBean.navCase}">
//						<f:param name="case" value="#{cc.attrs.caseObject.id}"/>
//					</h:commandLink>
//					<h:panelGroup styleClass="activity-item-multiple-element" layout="block">
//						<ul class="activity-item-element-list">
//							<ui:repeat value="#{activityObject.elements}" var="elementObject">
//								<li>
//									<h:outputText value="#{enum[elementObject.type.code]}"/>#{' '}
//									<h:commandLink value="#{elementObject.associationObject.code}" action="#{version.navVersion}" 
//										rendered="#{elementObject.type eq 'C_V' or elementObject.type eq 'A_V' or elementObject.type eq 'R_V'}">
//										<f:param name="version" value="#{elementObject.associationId}"/>
//									</h:commandLink>
//									<h:commandLink value="#{elementObject.associationObject.name}" action="#{component.navComponent}" 
//										rendered="#{elementObject.type eq 'A_C' or elementObject.type eq 'R_C'}">
//										<f:param name="component" value="#{elementObject.associationId}"/>
//									</h:commandLink>
//									<h:commandLink value="#{elementObject.associationObject.label}" action="#{label.navLabel}" 
//										rendered="#{elementObject.type eq 'A_A' or elementObject.type eq 'R_A'}">
//										<f:param name="label" value="#{elementObject.associationId}"/>
//									</h:commandLink>
//									<h:commandLink value="#{elementObject.associationObject.fullName}" action="#{employeeBeen.navEmployee}" 
//										rendered="#{elementObject.type eq 'ASS' or elementObject.type eq 'REP'}">
//										<f:param name="employee" value="#{elementObject.associationId}"/>
//									</h:commandLink>
//									<h:commandLink value="#{elementObject.associationObject.caseHeader}" action="#{caseBean.navCase}" 
//										rendered="#{elementObject.type eq 'A_L' or elementObject.type eq 'R_L'}">
//										<f:param name="case" value="#{elementObject.associationId}"/>
//									</h:commandLink>
//								</li>
//							</ui:repeat>
//						</ul>
//					</h:panelGroup>
//				</h:panelGroup>
//				<h:panelGroup rendered="#{activityObject.elements.size() eq 1}">
//					<h:outputText value="#{enum[singleElement.type.code]}"/>#{' '}
//					<h:commandLink value="#{singleElement.associationObject.code}" action="#{version.navVersion}" 
//						rendered="#{singleElement.type eq 'C_V' or singleElement.type eq 'A_V' or singleElement.type eq 'R_V'}">
//						<f:param name="version" value="#{singleElement.associationId}"/>
//					</h:commandLink>
//					<h:commandLink value="#{singleElement.associationObject.name}" action="#{component.navComponent}" 
//						rendered="#{singleElement.type eq 'A_C' or singleElement.type eq 'R_C'}">
//						<f:param name="component" value="#{singleElement.associationId}"/>
//					</h:commandLink>
//					<h:commandLink value="#{singleElement.associationObject.fullName}" action="#{employee.navEmployee}" 
//						rendered="#{singleElement.type eq 'ASS' or singleElement.type eq 'REP'}">
//						<f:param name="employee" value="#{singleElement.associationId}"/>
//					</h:commandLink>
//					<h:commandLink value="#{singleElement.associationObject.label}" action="#{label.navLabel}" 
//						rendered="#{singleElement.type eq 'A_A' or singleElement.type eq 'R_A'}">
//						<f:param name="label" value="#{singleElement.associationId}"/>
//					</h:commandLink>
//					<h:commandLink value="#{singleElement.associationObject.caseHeader}" action="#{caseBean.navCase}" 
//						rendered="#{singleElement.type eq 'A_L' or singleElement.type eq 'R_L'}">
//						<f:param name="case" value="#{singleElement.associationId}"/>
//					</h:commandLink>
//					<h:outputText value="#{singleElement.type eq 'C_V' or singleElement.type eq 'ASS' or singleElement.type eq 'REP' or singleElement.type eq 'STA' or singleElement.type eq 'STO'? ' on ' : ' '}"/>
//					<h:outputText value="#{singleElement.type eq 'A_A' or singleElement.type eq 'A_V' or singleElement.type eq 'A_C' or singleElement.type eq 'C_N' or singleElement.type eq 'C_A'  or singleElement.type eq 'A_L'? ' to ' : ' '}"/>
//					<h:outputText value="#{singleElement.type eq 'R_A' or singleElement.type eq 'R_V' or singleElement.type eq 'R_C' or singleElement.type eq 'D_A' or singleElement.type eq 'D_N' or singleElement.type eq 'R_L'? ' from ' : ' '}"/>
//					<h:outputText value="#{singleElement.type eq 'U_A' or singleElement.type eq 'U_N' ? ' of ' : ' '}"/>
//					<h:commandLink value="#{cc.attrs.caseObject.caseHeader}" action="#{caseBean.navCase}">
//						<f:param name="case" value="#{cc.attrs.caseObject.id}"/>
//					</h:commandLink>
//					<h:panelGroup styleClass="activity-item-description" layout="block" rendered="#{singleElement.type eq 'C_N' or singleElement.type eq 'U_N'}">
//						<h:outputText value="#{singleElement.associationObject.content}"/>
//					</h:panelGroup>
//				</h:panelGroup>
//			</h:panelGroup>
//			
//			<h:panelGroup styleClass="activity-item-date" layout="block">
//				<h:panelGroup><h:graphicImage styleClass="imglink" value="resources/img/office/#{cc.attrs.caseObject.type.imagePath}.png"/></h:panelGroup>
//				<h:panelGroup styleClass="space"></h:panelGroup>
//				<h:panelGroup><co:date date="#{activity.create}" recentForm="true" shortFormat="HH:mm" fullFormat="yy-MM-dd HH:mm"/></h:panelGroup>
//			</h:panelGroup>
//		</h:panelGroup>
//		<h:panelGroup styleClass="clearfix" layout="block"></h:panelGroup>
//	</h:panelGroup>
	public Case getCaseObject() {
		return caseObject;
	}

	public void setCaseObject(Case caseObject) {
		this.caseObject = caseObject;
	}

	public CaseActivity getActivityObject() {
		return activityObject;
	}

	public void setActivityObject(CaseActivity activityObject) {
		this.activityObject = activityObject;
	}

}
