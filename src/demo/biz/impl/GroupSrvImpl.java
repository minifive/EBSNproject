package demo.biz.impl;

import java.util.LinkedList;
import java.util.List;

import demo.biz.GroupSrv;
import demo.dao.GroupDAO;
import demo.vo.Group;
import demo.vo.User;

public class GroupSrvImpl implements GroupSrv{
	private GroupDAO groupDAO=new GroupDAO();

	public GroupDAO getGroupDAO() {
		return groupDAO;
	}

	public void setGroupDAO(GroupDAO groupDAO) {
		this.groupDAO = groupDAO;
	}

	@Override
	public Group dovalidate(Group group) {
		// TODO Auto-generated method stub
		Group u=groupDAO.findById(group.getGroupid());
		if (u != null){//如果该id不存在
			return u;
		}
		return null;
	}

	@Override
	public void dobuildgroup(Group group) {
		// TODO Auto-generated method stub
		groupDAO.save(group);
	}

	@Override
	public void dodelete(Group group) {
		// TODO Auto-generated method stub
		groupDAO.delete(group);
		
	}

	@Override
	public String addextingnum(String groupid) {
		// TODO Auto-generated method stub
		Group group=new Group();
		group.setGroupid(groupid);
		group=dovalidate(group);
		if(group==null)
			return "group not existing";
		int maxnum=Integer.parseInt(group.getMemberceiling());
		int extnum=Integer.parseInt(group.getExistmemnum());
		if(extnum>=maxnum)
			return "超出群组最大人数限定";
		extnum++;
		group.setExistmemnum(""+extnum);
		groupDAO.delete(group);//更新group的信息
		groupDAO.save(group);
		return "add success";
	}

	@Override
	public String delextingnum(String groupid,int size) {
		// TODO Auto-generated method stub
		Group group=new Group();
		group.setGroupid(groupid);
		group=dovalidate(group);
		int extnum=Integer.parseInt(group.getExistmemnum());
		if(extnum>=size)
		{
			extnum=extnum-size;
			group.setExistmemnum(""+extnum);
			groupDAO.delete(group);
			groupDAO.save(group);
			return "existing group-user num -1 success!";
		}
		return "failed ,existing number =0!!";
	}

	@Override
	public List<Group> FindByGroupname(Group group) {
		// TODO Auto-generated method stub
		List<Group> temp=new LinkedList<Group>();
		temp=groupDAO.findByGroupname(group.getGroupname());
		return temp;
	}

	@Override
	public Group doupdate(Group group) {
		// TODO Auto-generated method stub
		groupDAO.delete(group);
		groupDAO.save(group);
		return group;
	}
	
}