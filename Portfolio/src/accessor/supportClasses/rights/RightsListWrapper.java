package accessor.supportClasses.rights;

import java.util.List;

public class RightsListWrapper {
    private List<Rights> list;
    private int chosenUserId;
    private int numberOfAccessRights;
    private int numberOfAdminRights;

    public List<Rights> getList() {
        return list;
    }

    public void setList(List<Rights> list) {
        this.list = list;
    }
    
    public int getChosenUserId() {
		return chosenUserId;
	}



	public int getNumberOfAccessRights() {
		return numberOfAccessRights;
	}

	public void setNumberOfAccessRights(int numberOfAccessRights) {
		this.numberOfAccessRights = numberOfAccessRights;
	}

	public int getNumberOfAdminRights() {
		return numberOfAdminRights;
	}

	public void setNumberOfAdminRights(int numberOfAdminRights) {
		this.numberOfAdminRights = numberOfAdminRights;
	}

	public void setChosenUserId(int chosenUserId) {
		this.chosenUserId = chosenUserId;
	}

	public RightsListWrapper(List<Rights>list) {
    	this.list=list;
    	chosenUserId=0;
    }
    public RightsListWrapper() {
    }
    
    
}
