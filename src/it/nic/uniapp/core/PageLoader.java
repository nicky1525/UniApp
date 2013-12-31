package it.nic.uniapp.core;


import it.nic.uniapp.AddEsame;
import it.nic.uniapp.CalendarioEsami;
import it.nic.uniapp.FunzioniEsami;
import it.nic.uniapp.ListaEsami;
import it.nic.uniapp.MainActivity;
import it.nic.uniapp.UpdateEsame;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class PageLoader
{
	public PageLoader()
	{

	}

	public static enum PageType
	{
		Unknown, MainActivity, ListaEsami, FunzioniEsami, CalendarioEsami, AddEsame, UpdateEsame
	}

	public boolean startPageDependentActivity(Context ctx, PageType pageType, Boolean useStartActivityForResult, Bundle bundle)
	{
		boolean result = false;

		Intent intent = null;

		if (pageType != null)
		{
			if (pageType == PageType.Unknown)
			{
				return false;
			}

			if (pageType == PageType.MainActivity)
			{
				intent = new Intent(ctx, MainActivity.class);
			}
			
			else if (pageType == PageType.ListaEsami)
			{
				intent = new Intent(ctx, ListaEsami.class);
			}
			
			else if (pageType == PageType.FunzioniEsami)
			{
				intent = new Intent(ctx, FunzioniEsami.class);
			}
			
			else if (pageType == PageType.CalendarioEsami)
			{
				intent = new Intent(ctx, CalendarioEsami.class);
			}
			
			else if (pageType == PageType.AddEsame)
			{
				intent = new Intent(ctx, AddEsame.class);
			}
			
			else if (pageType == PageType.UpdateEsame)
			{
				intent = new Intent(ctx, UpdateEsame.class);
			}



			if (intent != null)
			{
				// se ho dichiarato il bundle allora lo aggiungo
				if (bundle != null)
				{
					intent.putExtras(bundle);
				}

				if (useStartActivityForResult)
				{
					((Activity) ctx).startActivityForResult(intent, 1);
				}
				else
				{
					ctx.startActivity(intent);
				}

				result = true;
			}
		}

		return result;
	}
}