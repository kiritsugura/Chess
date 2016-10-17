
import java.awt.*;
import java.util.*;
import java.util.List;
import javax.imageio.ImageIO;
import java.io.*;

public class BishopPiece extends ChessPieceClass {
	public BishopPiece(double x,double y,double width,double height,boolean isPo){
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		playerOne=isPo;
		//gets the image for piece
		try{
			if(playerOne){
				pieceImage=ImageIO.read(new File("FilteredTextures//bishop.png"));
			}else{
				pieceImage=ImageIO.read(new File("FilteredTextures//bishopTwo.png"));
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	//puts the Moves into a List of board-parts to be displayed
	public void Moves(ArrayList<BoardParts> open,ArrayList<BoardParts> enemyPieceSquares){
		for(int i=0;i<enemyPieceSquares.size();i++){
			if(Math.abs((enemyPieceSquares.get(i).getX()/50.0)-((this.x-5.0)/50.0))==Math.abs((enemyPieceSquares.get(i).getY()/50.0)-((this.y-5.0)/50.0))){
				pieceMoves.add(enemyPieceSquares.get(i));
			}
		}
		for(int index=0;index<open.size();index++){
			if(Math.abs(((open.get(index).getX()/50.0)-(this.x-5)/50.0))==(Math.abs(((open.get(index).getY())/50.0)-(this.y-5)/50.0))){
				pieceMoves.add(open.get(index));
			}
		}
		sortList(pieceMoves,enemyPieceSquares);
		canDraw=true;
	}
	//gets the proper item into the list(above method) by checking the x and y of the squares
	public void sortList(List<BoardParts> li,List<BoardParts> enemy){
		List<BoardParts> northWest=new ArrayList<BoardParts>();
		List<BoardParts> northEast=new ArrayList<BoardParts>();
		List<BoardParts> southEast=new ArrayList<BoardParts>();
		List<BoardParts> southWest=new ArrayList<BoardParts>();
		for(int i=0;i<li.size();i++){
			if(li.get(i).getX()<=this.x && li.get(i).getY()<=this.y-5.0){
				northWest.add(li.get(i));
			}
			else if(li.get(i).getX()>=this.x && li.get(i).getY()<=this.y-5.0){
				northEast.add(li.get(i));
			}
			else if(li.get(i).getX()>=this.x && li.get(i).getY()>=this.y-5.0){
				southEast.add(li.get(i));
			}
			else if(li.get(i).getX()<=this.x && li.get(i).getY()>=this.y-5.0){
				southWest.add(li.get(i));
			}
		}
		double tx=this.x-5.0;
		double ty=this.y-5.0;
		List<BoardParts> tli=new ArrayList<BoardParts>();
		if(!(northWest.isEmpty())){
			for(int i=0;i<li.size();i++){
				if(tx-50.0==li.get(i).getX() && ty-50.0==li.get(i).getY()){
					tx-=50.0;
					ty-=50;
					tli.add(li.get(i));
					if(enemy.contains(li.get(i))){
						break;
					}
					i=-1;
				}
			}
			tx=this.x-5.0;
			ty=this.y-5.0;
		}
		if(!(northEast.isEmpty())){
			for(int i=0;i<li.size();i++){
				if(tx+50.0==li.get(i).getX() && ty-50.0==li.get(i).getY()){
					tx+=50.0;
					ty-=50.0;
					tli.add(li.get(i));
					if(enemy.contains(li.get(i))){
						break;
					}
					i=-1;
				}
			}
			tx=this.x-5.0;
			ty=this.y-5.0;
		}
		if(!(southEast.isEmpty())){
			for(int i=0;i<li.size();i++){
				if(tx+50.0==li.get(i).getX() && ty+50.0==li.get(i).getY()){
					tx+=50.0;
					ty+=50.0;
					tli.add(li.get(i));
					if(enemy.contains(li.get(i))){
						break;
					}
					i=-1;
				}
			}
			tx=this.x-5.0;
			ty=this.y-5.0;
		}
		if(!(southWest.isEmpty())){
			for(int i=0;i<li.size();i++){
				if(tx-50.0==li.get(i).getX() && ty+50.0==li.get(i).getY()){
					tx-=50.0;
					ty+=50.0;
					tli.add(li.get(i));
					if(enemy.contains(li.get(i))){
						break;
					}
					i=-1;
				}
			}
			tx=this.x-5.0;
			ty=this.y-5.0;
		}
		li.removeAll(li);
		li.addAll(tli);
	}
}