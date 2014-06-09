package mu.codeoffice.entity.settings;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import mu.codeoffice.entity.Enterprise;

@Entity
@Table(name = "settings_issuenavigation", uniqueConstraints = @UniqueConstraint(columnNames = {"name", "enterprise_id"}))
public class IssueNavigationSettings implements SettingsEntity, Serializable {
	
	public static enum IssueColumn {

		CODE("issue.column.code"),
		SUMMARY("issue.column.summary"),
		TYPE("issue.column.type"),
		STATUS("issue.column.status"),
		PRIORITY("issue.column.priority"),
		RESOLUTION("issue.column.resolution"),
		CREATE("issue.column.create"),
		UPDATE("issue.column.update"),
		REPORTER("issue.column.reporter"),
		ASSIGNEE("issue.column.assignee"),
		PROJECT("issue.column.project"),
		RELEASE_VERSION("issue.column.releaseVersion"),
		AFFECT_VERSION("issue.column.affectVersion"),
		COMPONENT("issue.column.component");
		
		private final String key;
		
		private IssueColumn(String key) {
			this.key = key;
		}
		
		public String getKey() {
			return key;
		}
	}
	
	private static final long serialVersionUID = 1161364870054375601L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "enterprise_id")
	private Enterprise enterprise;
	
	@Column(name = "column_string")
	private String columnString;

	@Column(name = "name")
	private String name;
	
	@Transient
	private List<IssueColumn> columns;

	@Override
	public void setDefaultSettings(Properties properties) {
		columnString = properties.getProperty("settings.issuenavigation.default.columnString");
	}

	public List<IssueColumn> getColumns() {
		if (columns == null) {
			columns = new ArrayList<>();
			String[] split = columnString.split(",");
			for (String string : split) {
				columns.add(IssueColumn.valueOf(string.trim()));
			}
		} 
		return columns;
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

	public String getColumnString() {
		return columnString;
	}

	public void setColumnString(String columnString) {
		this.columnString = columnString;
	}

	public void setColumns(List<IssueColumn> columns) {
		this.columns = columns;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
