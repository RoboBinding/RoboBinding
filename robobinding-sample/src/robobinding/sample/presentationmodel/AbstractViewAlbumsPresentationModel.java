package robobinding.sample.presentationmodel;

import robobinding.presentationmodel.ItemClickEvent;
import robobinding.sample.CreateEditAlbumActivity;
import robobinding.sample.ViewAlbumActivity;
import robobinding.sample.dao.AlbumDao;
import robobinding.sample.model.Album;
import android.content.Context;
import android.content.Intent;

public abstract class AbstractViewAlbumsPresentationModel
{
	protected static final String PROPERTY_ALBUMS = "albums";
	
	protected Context context;
	protected AlbumDao albumDao;
	
	public AbstractViewAlbumsPresentationModel(Context context, AlbumDao albumDao)
	{
		this.context = context;
		this.albumDao = albumDao;
	}

	public void createAlbum()
	{
		context.startActivity(new Intent(context, CreateEditAlbumActivity.class));
	}

	public void viewAlbum(ItemClickEvent event)
	{
		Album album = albumDao.get(event.getId());
		
		Intent intent = new Intent(context, ViewAlbumActivity.class);
		intent.putExtra(ViewAlbumActivity.ALBUM_ID, album.getId());
		context.startActivity(intent);
	}
}