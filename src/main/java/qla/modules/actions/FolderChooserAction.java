package qla.modules.actions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import qla.modules.actions.exeption.ActionException;
import qla.modules.actions.models.AbstractActionCommand;
import qla.modules.actions.models.FolderChooserActionRQ;
import qla.modules.actions.models.FolderChooserActionRS;
import qla.modules.confuguration.AppConfiguration;
import qla.modules.stringutils.StringUtils;

public class FolderChooserAction extends AbstractAction<FolderChooserActionRQ>  {
	public FolderChooserAction(Class rqClass) {
		super(rqClass);
	}
	
	@Override
	public AbstractActionCommand proccess(FolderChooserActionRQ rq) throws ActionException {
		FolderChooserActionRS rs = new FolderChooserActionRS();
		String pathToFolder = rq.getFolder();
		if(StringUtils.isEmpty(pathToFolder)) {
			String propkey = rq.getPropKey();
			if(!StringUtils.isEmpty(propkey)) {
				pathToFolder = AppConfiguration.getProperty(propkey);
				if(pathToFolder == null) {
					throw new ActionException("Didn't find property value by key:" + propkey);
				}
			}
		}
		File folder = new File(pathToFolder);
		if(folder.exists()){
			if(folder.isDirectory()) {
				File [] files = folder.listFiles();
				List<String> filenames = new ArrayList<>();
				for (int i = 0; i < files.length; i++) {
					if(!files[i].isDirectory()) {
						filenames.add(files[i].getName());
					}
				}
				rs.setListOflogfiles(filenames);
			}
		} else {
			throw new ActionException("Folder by given path does not exist.");
		}
		rs.setSuccess("");
		return rs;
	}

}
