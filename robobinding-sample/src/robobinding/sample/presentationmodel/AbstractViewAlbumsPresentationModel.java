package robobinding.sample.presentationmodel;

import robobinding.binding.viewattribute.ItemClickEvent;
import robobinding.presentationmodel.AbstractPresentationModel;
import robobinding.sample.CreateEditAlbumActivity;
import robobinding.sample.ViewAlbumActivity;
import robobinding.sample.dao.AlbumDao;
import robobinding.sample.model.Album;
import android.app.Activity;
import android.content.Intent;

public abstract class AbstractViewAlbumsPresentationModel extends AbstractPresentationModel
{
	protected static final String PROPERTY_ALBUMS = "albums";

	protected Activity activity;
	protected AlbumDao albumDao;

	public AbstractViewAlbumsPresentationModel(Activity activity, AlbumDao albumDao)
	{
		this.activity = activity;
		this.albumDao = albumDao;
	}

	public void createAlbum()
	{
		activity.startActivity(new Intent(activity, CreateEditAlbumActivity.class));
	}

	public void viewAlbum(ItemClickEvent event)
	{
		Album album = albumDao.getAll().get(event.getPosition());

		Intent intent = new Intent(activity, ViewAlbumActivity.class);
		intent.putExtra(ViewAlbumActivity.ALBUM_ID, album.getId());
		activity.startActivityForResult(intent, 0);
	}
}