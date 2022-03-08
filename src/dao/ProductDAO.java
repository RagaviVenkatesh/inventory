package dao;

import java.sql.*;

import connectionManager.ConnectionManager;
import model.Product;

public class ProductDAO 
{
	public void addProduct(Product product) throws ClassNotFoundException, SQLException
	{
		ConnectionManager conn = new ConnectionManager();
		Connection con = conn.establishConnection();
		
		String sql = "insert into product(productId,productName,minsell,price,quantity)values(?,?,?,?,?)";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, product.getProductId());
		ps.setString(2, product.getProductName());
		ps.setInt(3, product.getMinSellQuantity());
		ps.setInt(4, product.getPrice());
		ps.setInt(5, product.getQuantity());
		ps.executeUpdate();
		
		conn.closeConnection();		
	}
	
	public void display() throws ClassNotFoundException, SQLException
	{
		ConnectionManager conn = new ConnectionManager();
		Connection con = conn.establishConnection();
		
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("select * from product");
		
		while(rs.next())
		{
			System.out.println("__________________________________________________________");
			System.out.println(rs.getInt("productId")+" | "+rs.getString("productName")+" | "
					+rs.getInt("minsell")+" | "+rs.getInt("price")+" | "+rs.getInt("quantity"));
		}
		conn.closeConnection();
	}
	
	public boolean quantityAvailable(int productid, int quantity) throws ClassNotFoundException, SQLException
	{
		ConnectionManager conn = new ConnectionManager();
		Connection con = conn.establishConnection();
		
		PreparedStatement st = con.prepareStatement("select quantity, minsell from product where productid=?");
		st.setInt(1,productid);
		ResultSet rs = st.executeQuery();
		
		if(rs.next())
		{
			if(quantity<=rs.getInt("quantity") && quantity<rs.getInt("minsell"))
			{
				conn.closeConnection();
				return true;
			}
			else
			{
				conn.closeConnection();
				return false;
			}
		}
		return false;
	}
	
	public int totalCost(int productid, int quantity) throws SQLException, ClassNotFoundException
	{
		ConnectionManager conn = new ConnectionManager();
		Connection con = conn.establishConnection();
		
		PreparedStatement st = con.prepareStatement("select price from product where productid=?");
		st.setInt(1,productid);
		ResultSet rs = st.executeQuery();
		
		int total=0;
		if(rs.next())
		{
			total = quantity*rs.getInt("price");
		}
		conn.closeConnection();
		return total;
	}
}
