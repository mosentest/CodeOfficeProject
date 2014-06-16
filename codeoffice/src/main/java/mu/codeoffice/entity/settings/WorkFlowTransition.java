package mu.codeoffice.entity.settings;

import java.io.Serializable;
import java.util.List;

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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import mu.codeoffice.entity.Enterprise;
import mu.codeoffice.security.ProjectPermission;

@Entity
@Table(name = "workflow_transition", 
	uniqueConstraints = @UniqueConstraint(columnNames = {"transition", "from_status_id", "to_status_id", "workflow_id"}))
public class WorkFlowTransition implements Serializable {

	private static final long serialVersionUID = 3289713503926256712L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "enterprise_id")
	private Enterprise enterprise;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "workflow_id")
	@NotNull
	private WorkFlow workFlow;
	
	@Column(name = "transition")
	@Size(min = 5, max = 30)
	private String transition;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "from_status_id")
	@NotNull
	private IssueStatus from;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "to_status_id")
	@NotNull
	private IssueStatus to;

	@Column(name = "required_permission_value")
	private int requiredPermissionValue;
	
	@Transient
	private List<ProjectPermission> requiredPermissions;
	
	public WorkFlowTransition() {}

    @Override
    public boolean equals(Object obj) {
    	if (!(obj instanceof WorkFlowTransition)) {
    		return false;
    	}
    	WorkFlowTransition o = (WorkFlowTransition) obj;
    	return o.id != null && this.id != null && o.id.equals(this.id);
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

	public WorkFlow getWorkFlow() {
		return workFlow;
	}

	public void setWorkFlow(WorkFlow workFlow) {
		this.workFlow = workFlow;
	}

	public String getTransition() {
		return transition;
	}

	public void setTransition(String transition) {
		this.transition = transition;
	}

	public IssueStatus getFrom() {
		return from;
	}

	public void setFrom(IssueStatus from) {
		this.from = from;
	}

	public IssueStatus getTo() {
		return to;
	}

	public void setTo(IssueStatus to) {
		this.to = to;
	}

	public List<ProjectPermission> getRequiredPermissions() {
		return requiredPermissions;
	}

	public void setRequiredPermissions(List<ProjectPermission> requiredPermissions) {
		this.requiredPermissions = requiredPermissions;
	}

	public int getRequiredPermissionValue() {
		return requiredPermissionValue;
	}

	public void setRequiredPermissionValue(int requiredPermissionValue) {
		this.requiredPermissionValue = requiredPermissionValue;
	}

}
