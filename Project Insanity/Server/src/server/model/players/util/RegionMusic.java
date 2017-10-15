package server.model.players.util;

import server.model.players.Client;

public class RegionMusic {
	
	public void playMusic(Client c) {
		Music song = getMusicId(c);
		if(song == null) {
			c.getPA().playMusic(125);
			return;
		}
		c.getPA().playMusic(song.music);
	}
	
	private Music getMusicId(Client c) {
		int x = c.getX(), y = c.getY();
		for(int i = 0; i < songs.length; i++) {
			if(x >= songs[i].swX && x <= songs[i].neX && y >= songs[i].swY && y <= songs[i].neY) {
				return songs[i];
			}
		}
		return null;
	}
	
        //Music id, swX, swY, neX, neY
	private final Music[] songs = {
		new Music(125, 3200, 3200, 3200, 3200),
	};
}
	