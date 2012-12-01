/**
 * Copyright 2011 Cheng Wei, Robert Taylor
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
package sample.robobinding.model;


/**
 * @since 1.0
 * @author Cheng Wei
 * @author Robert Taylor
 *
 */
public class Album
{
	public static final int NO_ID = -1;
	
	private long id;
	private String title;
	private String artist;
	private boolean classical;
	private String composer;
	
	private Album(Builder builder)
	{
		validate(builder);
		this.id = builder.id;
		this.title = builder.title;
		this.artist = builder.artist;
		this.classical = builder.classical;
		if(builder.isClassical())
		{
			this.composer = builder.composer;
		}
	}
	private void validate(Builder builder)
	{
		// TODO Auto-generated method stub
		
	}
	public long getId()
	{
		return id;
	}
	public void setId(long id)
	{
		this.id = id;
	}
	public String getTitle()
	{
		return title;
	}
	public String getArtist()
	{
		return artist;
	}
	public boolean isClassical()
	{
		return classical;
	}
	public String getComposer()
	{
		return composer;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Album other = (Album) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	public boolean isNew()
	{
		return isNew(id);
	}
	public Builder createBuilder()
	{
		Builder builder = new Builder();
		builder.setTitle(title);
		builder.setArtist(artist);
		builder.setClassical(classical);
		builder.setComposer(composer);
		builder.setId(id);
		return builder;
	}
	public static boolean isNew(long albumId)
	{
		return albumId == NO_ID;
	}
	
	public static class Builder
	{
		private long id = NO_ID;
		private String title;
		private String artist;
		private boolean classical;
		private String composer;
		public long getId()
		{
			return id;
		}
		public Builder setId(long id)
		{
			this.id = id;
			return this;
		}
		public String getTitle()
		{
			return title;
		}
		public Builder setTitle(String title)
		{
			this.title = title;
			return this;
		}
		public String getArtist()
		{
			return artist;
		}
		public Builder setArtist(String artist)
		{
			this.artist = artist;
			return this;
		}
		public boolean isClassical()
		{
			return classical;
		}
		public Builder setClassical(boolean classical)
		{
			this.classical = classical;
			return this;
		}
		public String getComposer()
		{
			return composer;
		}
		public Builder setComposer(String composer)
		{
			this.composer = composer;
			return this;
		}
		public boolean isNew()
		{
			return Album.isNew(id);
		}
		public Album create()
		{
			return new Album(this);
		}
	}

}
