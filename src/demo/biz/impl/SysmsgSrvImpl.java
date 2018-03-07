package demo.biz.impl;

import java.util.LinkedList;
import java.util.List;

import demo.biz.SysmsgSrv;
import demo.dao.SysmsgDAO;
import demo.vo.Sysmsg;

public class SysmsgSrvImpl implements SysmsgSrv{
	private SysmsgDAO sysmsgDAO;

	
	
	public SysmsgDAO getSysmsgDAO() {
		return sysmsgDAO;
	}

	public void setSysmsgDAO(SysmsgDAO sysmsgDAO) {
		this.sysmsgDAO = sysmsgDAO;
	}

	@Override
	public List<Sysmsg> MsgsforUser(String userid) {
		// TODO Auto-generated method stub
		List<Sysmsg> temp=new LinkedList<Sysmsg>();
		temp=sysmsgDAO.findByUserid(userid);
		return temp;
	}

	@Override
	public Sysmsg doValiad(Sysmsg sysmsg) {
		Sysmsg s=sysmsgDAO.findById(sysmsg.getMsgid());
		return s;
	}

	@Override
	public void doUpdate(Sysmsg sysmsg) {
		// TODO Auto-generated method stub
		sysmsgDAO.delete(sysmsg);
		sysmsgDAO.save(sysmsg);
	}

	@Override
	public Sysmsg AddSysmsg(Sysmsg sysmsg) {
		// TODO Auto-generated method stub
		sysmsgDAO.save(sysmsg);
		return sysmsg;
	}

	@Override
	public void doDelete(Sysmsg sysmsg) {
		sysmsgDAO.delete(sysmsg);
	}
}