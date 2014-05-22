package mu.codeoffice.entity.settings;

import java.io.Serializable;

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
@Table(name = "settings_global_advanced")
public class GlobalAdvancedSettings implements Serializable {
	
	private static final long serialVersionUID = 727855077877535985L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "enterprise_id")
	private Enterprise enterprise;

	@Column(name = "attachment_number_of_zip_entries")
	private int attachmentNumberOfZipEntries;
	
	@Column(name = "clone_prefix")
	private String clonePrefix;

	@Column(name = "date_time_java_format")
	private String dateTimeJavaFormat;

	@Column(name = "date_time_javascript_format")
	private String dateTimetJavascriptFormat;

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
	
	public GlobalAdvancedSettings() {}
	
}
