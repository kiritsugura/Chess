import java.awt.*;
import java.util.*;
import java.util.Iterator;
import java.util.List;
import javax.imageio.ImageIO;
import java.io.*;

//edit moves
public class QueenPiece extends ChessPieceClass {
	public QueenPiece(double x,double y,double width,double height,boolean isPo){
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		playerOne=isPo;
		try{
			if(playerOne){
				pieceImage=ImageIO.read(new File("FilteredTextures//queen.png"));
			}else{
				pieceImage=ImageIO.read(new File("FilteredTextures//queenTwo.png"));
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	//combine rook and bishop ones.
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
		List<BoardParts> diagonal=sortListDia(pieceMoves,enemyPieceSquares);
		pieceMoves.removeAll(pieceMoves);
		for(int index=0;index<enemyPieceSquares.size();index++){
			if(((enemyPieceSquares.get(index).getX()/50.0)==(this.x-5)/50.0) || (((enemyPieceSquares.get(index).getY())/50.0)==(this.y-5)/50.0)){
				pieceMoves.add(enemyPieceSquares.get(index));
			}
		}
		for(int index=0;index<open.size();index++){
			if(((open.get(index).getX()/50.0)==(this.x-5)/50.0) || (((open.get(index).getY())/50.0)==(this.y-5)/50.0)){
				pieceMoves.add(open.get(index));
			}
		}
		List<BoardParts> hv=sortListHorVer(pieceMoves,enemyPieceSquares);
		pieceMoves.removeAll(pieceMoves);
		pieceMoves.addAll(diagonal);
		pieceMoves.addAll(hv);
		canDraw=true;
	}
	public List<BoardParts> sortListDia(List<BoardParts> li,List<BoardParts> enemy){
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
		return tli;
	}
	public List<BoardParts> sortListHorVer(List<BoardParts> li,List<BoardParts> enemy){
		List<BoardParts> west=new ArrayList<BoardParts>();
		List<BoardParts> east=new ArrayList<BoardParts>();
		List<BoardParts> north=new ArrayList<BoardParts>();
		List<BoardParts> south=new ArrayList<BoardParts>();
		for(int i=0;i<li.size();i++){
			if(li.get(i).getX()<=this.x && li.get(i).getY()==this.y-5.0){
				west.add(li.get(i));
			}
			else if(li.get(i).getX()>=this.x && li.get(i).getY()==this.y-5.0){
				east.add(li.get(i));
			}
			else if(li.get(i).getY()<=this.y && li.get(i).getX()==this.x-5.0){
				north.add(li.get(i));
			}
			else if(li.get(i).getY()>=this.y && li.get(i).getX()==this.x-5.0){
				south.add(li.get(i));
			}
		}
		double tx=this.x-5.0;
		double ty=this.y-5.0;
		List<BoardParts> tli=new ArrayList<BoardParts>();
		if(!(west.isEmpty())){
			for(int i=0;i<li.size();i++){
				if(tx-50.0==li.get(i).getX()){
					tx-=50.0;
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
		if(!(east.isEmpty())){
			for(int i=0;i<li.size();i++){
				if(tx+50.0==li.get(i).getX()){
					tx+=50.0;
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
		if(!(north.isEmpty())){
			for(int i=0;i<li.size();i++){
				if(ty-50.0==li.get(i).getY()){
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
		if(!(south.isEmpty())){
			for(int i=0;i<li.size();i++){
				if(ty+50.0==li.get(i).getY()){
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
		return tli;
	}
}
