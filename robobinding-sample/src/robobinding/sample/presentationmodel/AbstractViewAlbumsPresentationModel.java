package robobinding.sample.presentationmodel;

import robobinding.binding.viewattribute.ItemClickEvent;
import robobinding.sample.CreateEditAlbumActivity;
import robobinding.sample.ViewAlbumActivity;
import robobinding.sample.model.Album;
import robobinding.sample.store.AlbumStore;
import android.content.Context;
import android.content.Intent;

public abstract class AbstractViewAlbumsPresentationModel
{
	protected static final String PROPERTY_ALBUMS = "albums";

	protected Context context;

	public AbstractViewAlbumsPresentationModel(Context context)
	{
		this.context = context;
	}

	public void createAlbum()
	{
		context.startActivity(new Intent(context, CreateEditAlbumActivity.class));
	}

	public void viewAlbum(ItemClickEvent event)
	{
		Album album = AlbumStore.getByIndex(event.getPosition());

		Intent intent = new Intent(context, ViewAlbumActivity.class);
		intent.putExtra(ViewAlbumActivity.ALBUM_ID, album.getId());
		context.startActivity(intent);
	}
	
}