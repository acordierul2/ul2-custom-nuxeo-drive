package fr.univlille2.ecm.drive;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nuxeo.drive.adapter.FileSystemItem;
import org.nuxeo.drive.adapter.FolderItem;
import org.nuxeo.drive.adapter.impl.DocumentBackedFolderItem;
import org.nuxeo.drive.service.NuxeoDriveManager;
import org.nuxeo.ecm.core.api.ClientException;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.runtime.api.Framework;

public class UserWorkspaceTopLevelFolderItem extends
		DocumentBackedFolderItem {

	private static final long serialVersionUID = 1L;

	private static final Log log = LogFactory.getLog(UserWorkspaceTopLevelFactory.class);
	
	protected DocumentModel userWorkspace;
	
	public UserWorkspaceTopLevelFolderItem(String factoryName, 
			DocumentModel userWorkspace, String folderName) 
						throws ClientException{
		super(factoryName, null, userWorkspace);
		name = folderName;
		canRename = false;
		canDelete = false;
		this.userWorkspace = userWorkspace;
		log.debug(String.format("Userworkspace Adapter initialized with folderName %s", name));
	}
	
	protected UserWorkspaceTopLevelFolderItem(){
		// Needed for JSON deserialization
	}
	
	@Override
    public void rename(String name) throws ClientException {
        throw new UnsupportedOperationException(
                "Cannot rename the top level folder item.");
    }
 
    @Override
    public void delete() throws ClientException {
        throw new UnsupportedOperationException(
                "Cannot delete the top level folder item.");
    }
 
    @Override
    public FileSystemItem move(FolderItem dest) throws ClientException {
        throw new UnsupportedOperationException(
                "Cannot move the top level folder item.");
    }
    
    public List<FileSystemItem>getChildren()throws ClientException {
    // Register user workspace as a sync root if not done already
    	if(!getNuxeoDriveManager().isSynchronizationRoot(principal, userWorkspace)){
    		getNuxeoDriveManager().registerSynchronizationRoot(principal, userWorkspace, getSession());
    	}
    	return super.getChildren();
    }
    
    protected NuxeoDriveManager getNuxeoDriveManager() {
        return Framework.getLocalService(NuxeoDriveManager.class);
    }
	
}
