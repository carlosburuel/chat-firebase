package mx.cburuel.chatapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * @author Carlos Buruel
 */

public class ActividadPrincipal
	extends AppCompatActivity
	implements View.OnClickListener
{
	TextView tvCondicion;
	Button btnSunny;
	Button btnFoggy;

	//Referencia a la base de datos
	DatabaseReference referenciaRaiz = FirebaseDatabase.getInstance().getReference();
	//Nueva referencia a condition
	DatabaseReference referenciaCondicion = referenciaRaiz.child("condition");

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actividad_principal);
		tvCondicion = (TextView) findViewById(R.id.tvCondicion);
		btnSunny = (Button) findViewById(R.id.sunny);
		btnFoggy = (Button) findViewById(R.id.foggy);
	}

	@Override
	protected void onStart()
	{
		super.onStart();
		if( referenciaCondicion!= null )
		{
			referenciaCondicion.addValueEventListener(new ValueEventListener()
			{
				@Override
				public void onDataChange(DataSnapshot dataSnapshot)
				{
					Object texto = dataSnapshot.getValue();
//				Object texto = dataSnapshot.getValue(String.class);
					tvCondicion.setText( texto != null ? texto.toString() : "" );
				}

				@Override
				public void onCancelled(DatabaseError databaseError)
				{

				}
			});

			btnSunny.setOnClickListener(this);
			btnFoggy.setOnClickListener(this);
		}
	}

	public static class Post
	{
		public String autor;
		public String titulo;

		public Post(String autor, String titulo)
		{
			this.autor = autor;
			this.titulo = titulo;
		}
	}

	@Override
	public void onClick(View o_VISTA)
	{
		switch(o_VISTA.getId())
		{
			case R.id.sunny:
//				Cuando el mapeo sera un json simple
//        Map<String, String> map = new HashMap<>();
//				map.put("nombre", "carlos1");
//				map.put("apellido", "buruel1");
				DatabaseReference referencePush = referenciaCondicion.push();
				referencePush.setValue(new Post("Carlos BO", "Pregunta"));
				break;
			case R.id.foggy:
				//Ingreso de contenido en una sola cadena
				referenciaCondicion.setValue("Texto de una sola linea!");
				break;
		}
	}
}