package mu.codeoffice.entity.settings;

import java.io.Serializable;
import java.util.Properties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import mu.codeoffice.entity.Enterprise;

@Entity
@Table(name = "settings_advancedglobal")
public class AdvancedGlobalSettings implements SettingsEntity, Serializable {
	
	private static final long serialVersionUID = 727855077877535985L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "enterprise_id")
	private Enterprise enterprise;

	@Column(name = "attachment_number_of_zip_entries")
	private int attachmentNumberOfZipEntries;
	
	@Column(name = "clone_prefix")
	private String clonePrefix;

	@Column(name = "date_time_java_format")
	private String dateTimeJavaFormat;

	@Column(name = "date_time_javascript_format")
	private String dateTimeJavascriptFormat;

	@Column(name = "date_java_format")
	private String dateJavaFormat;

	@Column(name = "date_javascript_format")
	private String dateJavascriptFormat;
	
	@Column(name = "table_columns_subtask")
	private String tableColumnsSubtask;

	@Column(name = "ascending_issue_action_order")
	private boolean ascendingIssueActionOrder;	

	@Column(name = "autocomplete_max_results")
	private int autocompleteMaxResults;

	@Column(name = "activity_stream_max_results")
	private int activityStreamMaxResults;

	@Column(name = "issue_stream_max_results")
	private int issueStreamMaxResults;
	
	public AdvancedGlobalSettings() {}
	
	@Override
	public void setDefaultSettings(Properties properties) {
		attachmentNumberOfZipEntries = Integer.parseInt(properties.getProperty("settings.globaladvanced.default.attachmentNumberOfZipEntries"));
		clonePrefix = properties.getProperty("settings.globaladvanced.default.clonePrefix");
		dateTimeJavaFormat = properties.getProperty("settings.globaladvanced.default.dateTimeJavaFormat");
		dateTimeJavascriptFormat = properties.getProperty("settings.globaladvanced.default.dateTimeJavascriptFormat");
		dateJavaFormat = properties.getProperty("settings.globaladvanced.default.dateJavaFormat");
		dateJavascriptFormat = properties.getProperty("settings.globaladvanced.default.dateJavascriptFormat");
		tableColumnsSubtask = properties.getProperty("settings.globaladvanced.default.tableColumnsSubtask");
		ascendingIssueActionOrder = Boolean.valueOf(properties.getProperty("settings.globaladvanced.default.ascendingIssueActionOrder"));
		autocompleteMaxResults = Integer.parseInt(properties.getProperty("settings.globaladvanced.default.autocompleteMaxResults"));
		activityStreamMaxResults = Integer.parseInt(properties.getProperty("settings.globaladvanced.default.activityStreamMaxResults"));
		issueStreamMaxResults = Integer.parseInt(properties.getProperty("settings.globaladvanced.default.issueStreamMaxResults"));
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Enterprise getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}

	public int getAttachmentNumberOfZipEntries() {
		return attachmentNumberOfZipEntries;
	}

	public void setAttachmentNumberOfZipEntries(int attachmentNumberOfZipEntries) {
		this.attachmentNumberOfZipEntries = attachmentNumberOfZipEntries;
	}

	public String getClonePrefix() {
		return clonePrefix;
	}

	public void setClonePrefix(String clonePrefix) {
		this.clonePrefix = clonePrefix;
	}

	public String getDateTimeJavaFormat() {
		return dateTimeJavaFormat;
	}

	public void setDateTimeJavaFormat(String dateTimeJavaFormat) {
		this.dateTimeJavaFormat = dateTimeJavaFormat;
	}

	public String getDateTimeJavascriptFormat() {
		return dateTimeJavascriptFormat;
	}

	public void setDateTimeJavascriptFormat(String dateTimeJavascriptFormat) {
		this.dateTimeJavascriptFormat = dateTimeJavascriptFormat;
	}

	public String getDateJavaFormat() {
		return dateJavaFormat;
	}

	public void setDateJavaFormat(String dateJavaFormat) {
		this.dateJavaFormat = dateJavaFormat;
	}

	public String getDateJavascriptFormat() {
		return dateJavascriptFormat;
	}

	public void setDateJavascriptFormat(String dateJavascriptFormat) {
		this.dateJavascriptFormat = dateJavascriptFormat;
	}

	public String getTableColumnsSubtask() {
		return tableColumnsSubtask;
	}

	public void setTableColumnsSubtask(String tableColumnsSubtask) {
		this.tableColumnsSubtask = tableColumnsSubtask;
	}

	public boolean isAscendingIssueActionOrder() {
		return ascendingIssueActionOrder;
	}

	public void setAscendingIssueActionOrder(boolean ascendingIssueActionOrder) {
		this.ascendingIssueActionOrder = ascendingIssueActionOrder;
	}

	public int getAutocompleteMaxResults() {
		return autocompleteMaxResults;
	}

	public void setAutocompleteMaxResults(int autocompleteMaxResults) {
		this.autocompleteMaxResults = autocompleteMaxResults;
	}

	public int getActivityStreamMaxResults() {
		return activityStreamMaxResults;
	}

	public void setActivityStreamMaxResults(int activityStreamMaxResults) {
		this.activityStreamMaxResults = activityStreamMaxResults;
	}

	public int getIssueStreamMaxResults() {
		return issueStreamMaxResults;
	}

	public void setIssueStreamMaxResults(int issueStreamMaxResults) {
		this.issueStreamMaxResults = issueStreamMaxResults;
	}
	
}
