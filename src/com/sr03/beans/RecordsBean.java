package com.sr03.beans;

import com.sr03.dao.DAOFactory;
import com.sr03.dao.RecordDAO;
import com.sr03.entities.RecordEntity;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServlet;
import java.util.ArrayList;

@ManagedBean
@ViewScoped
public class RecordsBean extends HttpServlet {
    private ArrayList<RecordEntity> userRecords;
    private RecordDAO recordDAO;

    public RecordsBean() {
        this.recordDAO = DAOFactory.getInstance().getRecordDAO();
        this.userRecords = recordDAO.getAllUserRecords((Long) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("id"));
    }

    public ArrayList<RecordEntity> getUserRecords() {
        return userRecords;
    }

    public void setUserRecords(ArrayList<RecordEntity> userRecords) {
        this.userRecords = userRecords;
    }

    public RecordDAO getRecordDAO() {
        return recordDAO;
    }

    public void setRecordDAO(RecordDAO recordDAO) {
        this.recordDAO = recordDAO;
    }
}
