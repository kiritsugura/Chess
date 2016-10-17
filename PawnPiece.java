import java.util.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.*;

public class PawnPiece extends ChessPieceClass {
	private boolean firstMove=true;
	private double ir=0;
	public PawnPiece(double x,double y,double width,double height,boolean isPo){
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		playerOne=isPo;
		try{
			if(playerOne){
				pieceImage=ImageIO.read(new File("FilteredTextures//pawn.png"));
			}else{
				pieceImage=ImageIO.read(new File("FilteredTextures//pawnTwo.png"));
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	public boolean getFirstMove(){
		return firstMove;
	}
	public void setFirstMove(boolean b){
		firstMove=b;
	}
	//puts possible moves in a List to be displayed later.
	public void Moves(ArrayList<BoardParts> open,ArrayList<BoardParts> enemyPieceSquares){
		if(playerOne)
			ir=50.0;
		else
			ir=-50.0;
		for(int i=0;i<open.size();i++){
			if((open.get(i).isInside(this.x+ir,this.y))|| (open.get(i).isInside(this.x+(ir+ir),this.y) && firstMove)){
				pieceMoves.add(open.get(i));
			}
		}
		for(int in=0;in<pieceMoves.size();in++){
			if((pieceMoves.get(in).isInside(this.x+(ir+ir),this.y) && firstMove) && pieceMoves.size()==1){
				pieceMoves.removeAll(pieceMoves);
			}
		}
		for(int index=0;index<enemyPieceSquares.size();index++){
			if(enemyPieceSquares.get(index).isInside(this.x+ir,y+ir)|| enemyPieceSquares.get(index).isInside(this.x+ir,y-ir))
				pieceMoves.add(enemyPieceSquares.get(index));
			if(enemyPieceSquares.get(index).isInside(this.x+ir, this.y)){
				pieceMoves.remove(enemyPieceSquares.get(index));
			}
		}
		canDraw=true;
	}
}
