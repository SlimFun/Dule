package cn.edu.dule.beans;

public class WhereJPQL {
	
	private StringBuffer jpql = new StringBuffer(" where ");
	
	public WhereJPQL addEqual(String res, Object target){
		String targetStr = target.toString();
		if(target instanceof String){
			targetStr = "'" + target + "'";
		}
		jpql.append(res)
			.append("=")
			.append(targetStr)
			.append(" and ");
		return this;
	}
	
	public WhereJPQL addLike(String res, Object target){
		String targetStr = target.toString();
		if(target instanceof String){
			targetStr = "'" + target + "'";
		}
		jpql.append(res)
			.append(" like ")
			.append(targetStr)
			.append(" and ");
		return this;
	}
	
	public String generateWhereJPQL(){
		return jpql.substring(0, jpql.length() - " and ".length()).toString();
	}
}
