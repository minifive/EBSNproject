package demo.biz;

import java.util.List;

import demo.vo.Sysmsg;

public interface SysmsgSrv{
	List<Sysmsg> MsgsforUser(String userid);
	
	Sysmsg doValiad(Sysmsg sysmsg);
	
	void doUpdate(Sysmsg sysmsg);
	
	Sysmsg AddSysmsg(Sysmsg sysmsg);
	
	void doDelete(Sysmsg sysmsg);
}