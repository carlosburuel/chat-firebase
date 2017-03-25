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
	TextView tvUsuario;
	TextView tvPregunta;
	Button btnSunny;
	Button btnFoggy;

	//Referencia a la base de datos
	DatabaseReference referenciaRaiz = FirebaseDatabase.getInstance().getReference();
	//Nueva referencia a condition
	DatabaseReference referenciaUsuarios = referenciaRaiz.child("usuarios");
	DatabaseReference referenciaPreguntas = referenciaRaiz.child("preguntas");

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actividad_principal);
		tvUsuario = (TextView) findViewById(R.id.tvUsuario);
		tvPregunta = (TextView) findViewById(R.id.tvPregunta);
		btnSunny = (Button) findViewById(R.id.usuario);
		btnFoggy = (Button) findViewById(R.id.pregunta);
	}

	@Override
	protected void onStart()
	{
		super.onStart();
		referenciaUsuarios.addValueEventListener(new ValueEventListener()
		{
			@Override
			public void onDataChange(DataSnapshot dataSnapshot)
			{
				Object texto = dataSnapshot.getValue();
				tvUsuario.setText( texto != null ? texto.toString() : "" );
			}

			@Override
			public void onCancelled(DatabaseError databaseError)
			{

			}
		});

		referenciaPreguntas.addValueEventListener(new ValueEventListener()
		{
			@Override
			public void onDataChange(DataSnapshot dataSnapshot)
			{
				Object texto = dataSnapshot.getValue();
				tvPregunta.setText( texto != null ? texto.toString() : "" );
			}

			@Override
			public void onCancelled(DatabaseError databaseError)
			{

			}
		});

		btnSunny.setOnClickListener(this);
		btnFoggy.setOnClickListener(this);
	}

	@Override
	public void onClick(View o_VISTA)
	{
		switch(o_VISTA.getId())
		{
			case R.id.usuario:
//				Cuando el mapeo sera un json simple
//        Map<String, String> map = new HashMap<>();
//				map.put("nombre", "carlos1");
//				map.put("apellido", "buruel1");
				DatabaseReference referencePush = referenciaUsuarios.push();
				referencePush.setValue(new Usuarios("Carlos BO", "Pregunta"));
				break;
			case R.id.pregunta:
				//Ingreso de contenido en una sola cadena
				referenciaPreguntas.setValue(new Pregunta("Pregunta N"));
				break;
		}
	}

	public static class Usuarios
	{
		public String nombre;
		public String apellido;

		Usuarios(String nombre, String apellido)
		{
			this.nombre = nombre;
			this.apellido = apellido;
		}
	}

	public static class Pregunta
	{
		public String contenido;

		public Pregunta(String contenido)
		{
			this.contenido = contenido;
		}
	}
}
//http://codehero.co/mongodb-desde-cero-modelado-de-datos/