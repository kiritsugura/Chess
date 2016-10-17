import java.util.*;
import java.awt.*;

//The tiles of the Board
public class BoardParts{
	private ChessPieceClass[] aiChecking;
	private double x,y,width,height;
	private int blackOrWhite;
	private boolean hasPiece=false;
	private ChessPieceClass piece;
	public BoardParts(double x,double y,double width,double height){
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		aiChecking=ChessPieceClass.getAllPieces();
	}
	//for different colored tiles
	public void draw(Graphics2D g){
		if(blackOrWhite%2==1)
			g.setColor(Color.white);
		else
			g.setColor(Color.gray);
		g.fillRect((int)x,(int)y,(int)(width),(int)(height));
	}
	public double getX(){
		return x;
	}
	public double getY(){
		return y;
	}
	public double getWidth(){
		return width;
	}
	public double getHeight(){
		return height;
	}
	public void setBOR(int i){
		blackOrWhite=i;
	}
	public boolean hasPiece(){
		return hasPiece;
	}
	//piece on the squares
	public ChessPieceClass getPieceOnSquare(){
		return piece;
	}
	public void setPieceOnSquare(ChessPieceClass p){
		piece=p;
		hasPiece=true;
	}
	//if the piece is moved else where
	public void pieceRemoved(){
		piece=null;
		hasPiece=false;
	}
	//for finding if a possible move
	public boolean isInside(double px,double py){
		if(this.x<px && this.y<py && this.x+this.width>px && this.y+this.height>py)
			return true;
		return false;
	}
	//can change later, for AI purposes
}
