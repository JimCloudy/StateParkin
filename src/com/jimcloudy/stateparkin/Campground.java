package com.jimcloudy.stateparkin;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.ScrollView;

public class Campground extends Activity {
	LinearLayout lytImages;
	RatingBar rtgOverall;
	RatingBar rtgFacilities;
	RatingBar rtgTrails;
	RatingBar rtgTent;
	RatingBar rtgCabin;
	RatingBar rtgCamper;
	RatingBar rtgFishing;
	RatingBar rtgBird;
	ScrollView scrollView;
	EditText editLikes;
	EditText editDislikes;
	ParkData parkRating;
	ImageView imgFacilitiesNone;
	ImageView imgTrailsNone;
	ImageView imgTentNone;
	ImageView imgCabinNone;
	ImageView imgCamperNone;
	ImageView imgFishingNone;
	ImageView imgBirdNone;
	String[] park;
	Rating parkRtg;
	
	private class Rating{
		int numRatings = 0;
		int totRatings = 0;
		int ovrRating = 0;
		
		public int getRating(){
			numRatings = 0;
			totRatings = 0;
			if(rtgFacilities.getProgress() != 0){
				numRatings++;
				totRatings+=rtgFacilities.getProgress();
			}
			if(rtgTrails.getProgress() != 0){
				numRatings++;
				totRatings+=rtgTrails.getProgress();
			}
			if(rtgTent.getProgress() != 0){
				numRatings++;
				totRatings+=rtgTent.getProgress();
			}
			if(rtgCabin.getProgress() != 0){
				numRatings++;
				totRatings+=rtgCabin.getProgress();
			}
			if(rtgCamper.getProgress() != 0){
				numRatings++;
				totRatings+=rtgCamper.getProgress();
			}
			if(rtgFishing.getProgress() != 0){
				numRatings++;
				totRatings+=rtgFishing.getProgress();
			}
			if(rtgBird.getProgress() != 0){
				numRatings++;
				totRatings+=rtgBird.getProgress();
			}
			ovrRating = Math.round((float)totRatings/numRatings);
			return Math.round(ovrRating);
		}
	}
	
	private OnRatingBarChangeListener changeListener = new OnRatingBarChangeListener() {
		@Override
		public void onRatingChanged(RatingBar bar, float rtg, boolean user) {
			switch(bar.getId()){
				case R.id.rtgFacilities :
					imgFacilitiesNone.setImageResource(R.drawable.none_empty);
					break;
				case R.id.rtgTrails :
					imgTrailsNone.setImageResource(R.drawable.none_empty);
					break;
				case R.id.rtgTent :
					imgTentNone.setImageResource(R.drawable.none_empty);
					break;
				case R.id.rtgCabin :
					imgCabinNone.setImageResource(R.drawable.none_empty);
					break;
				case R.id.rtgCamper :
					imgCamperNone.setImageResource(R.drawable.none_empty);
					break;
				case R.id.rtgFishing :
					imgFishingNone.setImageResource(R.drawable.none_empty);
					break;
				case R.id.rtgBird :
					imgBirdNone.setImageResource(R.drawable.none_empty);
					break;
			}
			rtgOverall.setProgress(parkRtg.getRating());
		}
	};
	
	private OnClickListener clickListener = new OnClickListener() {

        public void onClick(View v) {
        	switch(v.getId()){
        	case R.id.imgFacilitiesNone :
        		rtgFacilities.setProgress(0);
        		imgFacilitiesNone.setImageResource(R.drawable.none_full);
        		break;
        	case R.id.imgTrailsNone :
        		rtgTrails.setProgress(0);
        		imgTrailsNone.setImageResource(R.drawable.none_full);
        		break;
        	case R.id.imgTentNone :
        		rtgTent.setProgress(0);
        		imgTentNone.setImageResource(R.drawable.none_full);
        		break;
        	case R.id.imgCabinNone :
        		rtgCabin.setProgress(0);
        		imgCabinNone.setImageResource(R.drawable.none_full);
        		break;
        	case R.id.imgCamperNone :
        		rtgCamper.setProgress(0);
        		imgCamperNone.setImageResource(R.drawable.none_full);
        		break;
        	case R.id.imgFishingNone :
        		rtgFishing.setProgress(0);
        		imgFishingNone.setImageResource(R.drawable.none_full);
        		break;
        	case R.id.imgBirdNone :
        		rtgBird.setProgress(0);
        		imgBirdNone.setImageResource(R.drawable.none_full);
        		break;
        	}
        	rtgOverall.setProgress(parkRtg.getRating());
        }
	};
		
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campground);
        lytImages = (LinearLayout) findViewById(R.id.lytImages);
        rtgOverall = (RatingBar) findViewById(R.id.rtgOverall);
        rtgFacilities = (RatingBar) findViewById(R.id.rtgFacilities);
        rtgTrails = (RatingBar) findViewById(R.id.rtgTrails);
        rtgTent = (RatingBar) findViewById(R.id.rtgTent);
        rtgCabin = (RatingBar) findViewById(R.id.rtgCabin);
        rtgCamper = (RatingBar) findViewById(R.id.rtgCamper);
        rtgFishing = (RatingBar) findViewById(R.id.rtgFishing);
        rtgBird = (RatingBar) findViewById(R.id.rtgBird);
        scrollView = (ScrollView) findViewById(R.id.scrCampground);
        editLikes = (EditText) findViewById(R.id.editLikes);
        editDislikes = (EditText) findViewById(R.id.editDislikes);
        imgFacilitiesNone = (ImageView) findViewById(R.id.imgFacilitiesNone);
        imgTrailsNone = (ImageView) findViewById(R.id.imgTrailsNone);
        imgTentNone = (ImageView) findViewById(R.id.imgTentNone);
        imgCabinNone = (ImageView) findViewById(R.id.imgCabinNone);
        imgCamperNone = (ImageView) findViewById(R.id.imgCamperNone);
        imgFishingNone = (ImageView) findViewById(R.id.imgFishingNone);
        imgBirdNone = (ImageView) findViewById(R.id.imgBirdNone);
        parkRtg = new Rating();
        
        rtgOverall.setProgress(1);
        scrollView.setVerticalScrollBarEnabled(false);
        
        parkRating = new ParkData(this);
        park = new String[]{"camden"};

        Cursor rating = null;
        try{
        	 rating = parkRating.getParkRating(park);
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
        if(rating != null)
        {
        	rtgFacilities.setProgress(rating.getInt(rating.getColumnIndex(ParkData.C_FACILITIES)));
        	if(rtgFacilities.getProgress()==0)
        	{
        		imgFacilitiesNone.setImageResource(R.drawable.none_full);
        	}
       		rtgTrails.setProgress(rating.getInt(rating.getColumnIndex(ParkData.C_TRAILS)));
       		if(rtgTrails.getProgress()==0)
        	{
        		imgTrailsNone.setImageResource(R.drawable.none_full);
        	}
       		rtgTent.setProgress(rating.getInt(rating.getColumnIndex(ParkData.C_TENT)));
       		if(rtgTent.getProgress()==0)
        	{
        		imgTentNone.setImageResource(R.drawable.none_full);
        	}
       		rtgCabin.setProgress(rating.getInt(rating.getColumnIndex(ParkData.C_CABIN)));
       		if(rtgCabin.getProgress()==0)
        	{
        		imgCabinNone.setImageResource(R.drawable.none_full);
        	}
       		rtgCamper.setProgress(rating.getInt(rating.getColumnIndex(ParkData.C_CAMPER)));
       		if(rtgCamper.getProgress()==0)
        	{
        		imgCamperNone.setImageResource(R.drawable.none_full);
        	}
       		rtgFishing.setProgress(rating.getInt(rating.getColumnIndex(ParkData.C_FISHING)));
       		if(rtgFishing.getProgress()==0)
        	{
        		imgFishingNone.setImageResource(R.drawable.none_full);
        	}
       		rtgBird.setProgress(rating.getInt(rating.getColumnIndex(ParkData.C_BIRD)));
       		if(rtgBird.getProgress()==0)
        	{
        		imgBirdNone.setImageResource(R.drawable.none_full);
        	}
       		editLikes.setText(rating.getString(rating.getColumnIndex(ParkData.C_LIKES)));
       		editDislikes.setText(rating.getString(rating.getColumnIndex(ParkData.C_DISLIKES)));
       		if(!rating.isClosed())
            {
            	rating.close();
            }
        }
        else
        {
        	ContentValues values = new ContentValues();
        	values.put(ParkData.C_ID, "camden");
        	values.put(ParkData.C_FACILITIES, 0);
        	values.put(ParkData.C_TRAILS, 0);
        	values.put(ParkData.C_TENT, 0);
        	values.put(ParkData.C_CABIN, 0);
        	values.put(ParkData.C_CAMPER, 0);
        	values.put(ParkData.C_FISHING, 0);
        	values.put(ParkData.C_BIRD, 0);
        	parkRating.insertOrIgnore(values);
        }
          
        rtgOverall.setProgress(parkRtg.getRating());

        rtgFacilities.setOnRatingBarChangeListener(changeListener);
        rtgTrails.setOnRatingBarChangeListener(changeListener);
        rtgTent.setOnRatingBarChangeListener(changeListener);
        rtgCabin.setOnRatingBarChangeListener(changeListener);
        rtgCamper.setOnRatingBarChangeListener(changeListener);
        rtgFishing.setOnRatingBarChangeListener(changeListener);
        rtgBird.setOnRatingBarChangeListener(changeListener);
        imgFacilitiesNone.setOnClickListener(clickListener);
        imgTrailsNone.setOnClickListener(clickListener);
        imgTentNone.setOnClickListener(clickListener);
        imgCabinNone.setOnClickListener(clickListener);
        imgCamperNone.setOnClickListener(clickListener);
        imgFishingNone.setOnClickListener(clickListener);
        imgBirdNone.setOnClickListener(clickListener);
        
        		
        //File sdcard = Environment.getExternalStorageDirectory();
        //File file = new File(sdcard,"*.jpg");
        //scrollView.addView(child, width, height)
    }
    
    public void onPause(){
    	super.onPause();
    	ContentValues values = new ContentValues();
    	values.put(ParkData.C_FACILITIES, rtgFacilities.getProgress());
    	values.put(ParkData.C_TRAILS, rtgTrails.getProgress());
    	values.put(ParkData.C_TENT, rtgTent.getProgress());
    	values.put(ParkData.C_CABIN, rtgCabin.getProgress());
    	values.put(ParkData.C_CAMPER, rtgCamper.getProgress());
    	values.put(ParkData.C_FISHING, rtgFishing.getProgress());
    	values.put(ParkData.C_BIRD, rtgBird.getProgress());
    	values.put(ParkData.C_LIKES, editLikes. getText().toString());
    	values.put(ParkData.C_DISLIKES, editDislikes.getText().toString());
    	parkRating.updateParkRating(park, values);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_campground, menu);
        return true;
    }
}
