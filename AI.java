import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.util.Random.*;
public class AI{
	private ArrayList<ChessPieceClass> aiPieces;
	private ArrayList<ChessPieceClass> playerPieces;
	private TotalBoard board;
	private BoardParts move;
	private ChessPieceClass piecea;
	private boolean isWon;
	public AI(ArrayList<ChessPieceClass> ai, ArrayList<ChessPieceClass> player, TotalBoard b){
		aiPieces=ai;
		playerPieces=player;
		board=b;
	}
	public void updateAI(ArrayList<ChessPieceClass> ai, ArrayList<ChessPieceClass> player, TotalBoard b){
		aiPieces=ai;
		playerPieces=player;
		board=b;
	}
	public void resetStuff(){
		move=null;
		piecea=null;
	}
	//AI decision making progress
	public void decideMoves(int turnCounter){
		ArrayList<ChessPieceClass> pieces=new ArrayList<ChessPieceClass>();
		ArrayList<BoardParts> list=new ArrayList<BoardParts>();
		for(int i=0;i<aiPieces.size();i++){
			aiPieces.get(i).Moves(board.findOpenSpots(aiPieces.get(i)),board.searchForEnemyPieces(turnCounter));
			ArrayList<BoardParts> parts=aiPieces.get(i).getPieceMoves();
			for(int in=0;in<parts.size();in++){
				for(int ind=0;ind<playerPieces.size();ind++){
					if(playerPieces.get(ind)==parts.get(in).getPieceOnSquare()){
						list.add(parts.get(in));
						pieces.add(aiPieces.get(i));
						if(parts.get(in).getPieceOnSquare() instanceof KingPiece && playerPieces.get(ind).isPlayerOne()){
							piecea=aiPieces.get(i);
							move=parts.get(in);
							isWon=true;
							break;
						}
					if(isWon)
						break;
					}
				if(isWon)
					break;
				}
			aiPieces.get(i).cancelPiece();
			if(isWon)
				break;
			}
		}
		if(!isWon){
			if(list.size()>=1){
				int choice=new Random().nextInt(list.size());
				piecea=pieces.get(choice);
				move=list.get(choice);
			}else{
				while(list.size()==0){
					piecea=aiPieces.get(new Random().nextInt(aiPieces.size()-1));
					piecea.Moves(board.findOpenSpots(piecea),board.searchForEnemyPieces(turnCounter));
					list=piecea.getPieceMoves();
				}
				if(list.size()-1>1)
					move=list.get(new Random().nextInt(list.size()-1));
				else
					move=list.get(0);
			}
		}
		piecea.cancelPiece();
		isWon=false;
		list.removeAll(list);
		pieces.removeAll(pieces);
	}
	public ChessPieceClass getPiece(){
		return piecea;
	}
	public BoardParts getMove(){
		return move;
	}
	public void reset(){
		piecea=null;
		move=null;
	}
	public void removeFromTeam(int turnCounter,ChessPieceClass p){
		if(turnCounter%2==1)
			aiPieces.remove(p);
		else
			playerPieces.remove(p);
	}
}