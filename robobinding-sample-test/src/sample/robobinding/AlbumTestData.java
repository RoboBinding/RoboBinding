/**
 * Copyright 2012 Cheng Wei, Robert Taylor
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 */
package sample.robobinding;

import java.util.Random;

import sample.robobinding.model.Album;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class AlbumTestData
{
	public static Album createAlbum()
	{
		boolean classical = new Random().nextBoolean();
		
		if(classical)
		{
			return createClassicalAlbum();
		}else
		{
			return createNonClassicalAlbum();
		}
	}
	
	public static Album createClassicalAlbum()
	{
		int index = nextUniqueIndex();
		Album.Builder albumBuilder = newAlbumBuilder(index);
		albumBuilder.setClassical(true);
		albumBuilder.setComposer("Composer "+index);
		return albumBuilder.create();
	}
	
	public static Album createNonClassicalAlbum()
	{
		int index = nextUniqueIndex();
		Album.Builder albumBuilder = newAlbumBuilder(index);
		return albumBuilder.create();
	}
	
	private static Album.Builder newAlbumBuilder(int index)
	{
		Album.Builder albumBuilder = new Album.Builder();
		albumBuilder.setArtist("Artist " + index);
		albumBuilder.setTitle("Album " +index);
		return albumBuilder;
	}
	
	private static int uniqueIndex = 1;
	private static int nextUniqueIndex()
	{
		return uniqueIndex++;
	}
}
