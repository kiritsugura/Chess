import java.awt.*;
import java.util.List;
import java.util.*;
import javax.imageio.ImageIO;
import java.io.*;

public class KnightPiece extends ChessPieceClass {
	public KnightPiece(double x,double y,double width,double height,boolean isPo){
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		playerOne=isPo;
		try{
			if(playerOne){
				pieceImage=ImageIO.read(new File("FilteredTextures//knight.png"));
			}else{
				pieceImage=ImageIO.read(new File("FilteredTextures//knightTwo.png"));
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	public void Moves(ArrayList<BoardParts> open,ArrayList<BoardParts> enemyPieceSquares){
		for(int i=0;i<open.size();i++){
			if(open.get(i).isInside(this.x+50.0,this.y+100.0)||open.get(i).isInside(this.x+50.0,this.y-100.0)||open.get(i).isInside(this.x-50.0,this.y+100.0)||open.get(i).isInside(this.x-50.0,this.y-100.0)||
			   open.get(i).isInside(this.x+100.0,this.y+50.0)||open.get(i).isInside(this.x+100.0,this.y-50.0)||open.get(i).isInside(this.x-100.0,this.y+50.0)||open.get(i).isInside(this.x-100.0,this.y-50.0))
				pieceMoves.add(open.get(i));
		}
		for(int i=0;i<enemyPieceSquares.size();i++){
			if(enemyPieceSquares.get(i).isInside(this.x+50.0,this.y+100.0)||enemyPieceSquares.get(i).isInside(this.x+50.0,this.y-100.0)||
			   enemyPieceSquares.get(i).isInside(this.x-50.0,this.y+100.0)||enemyPieceSquares.get(i).isInside(this.x-50.0,this.y-100.0)||
			   enemyPieceSquares.get(i).isInside(this.x+100.0,this.y+50.0)||enemyPieceSquares.get(i).isInside(this.x+100.0,this.y-50.0)||
			   enemyPieceSquares.get(i).isInside(this.x-100.0,this.y+50.0)||enemyPieceSquares.get(i).isInside(this.x-100.0,this.y-50.0))
					pieceMoves.add(enemyPieceSquares.get(i));
		}
		canDraw=true;
	}
}
