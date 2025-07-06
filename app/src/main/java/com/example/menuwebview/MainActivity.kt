package com.example.menuwebview

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.widget.Button
import android.widget.Switch
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerNoticias: RecyclerView
    private lateinit var adapter: NoticiaAdapter
    private var listaNoticias = mutableListOf<Noticia>()
    private var posicionSeleccionada: Int = -1 // Para el context menu
    private lateinit var tvContador: TextView
    private lateinit var btnResetear: Button
    private lateinit var prefs: SharedPreferences
    private var contadorVisitas = 0
    private lateinit var switchModoOscuro: Switch
    private var esModoOscuro = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar SharedPreferences
        prefs = getSharedPreferences("mis_preferencias", MODE_PRIVATE)

        // Referencias a views
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        tvContador = findViewById(R.id.tvContador)
        btnResetear = findViewById(R.id.btnResetear)
        switchModoOscuro = findViewById(R.id.switchModoOscuro)

        // --------------------------
        // CONTADOR DE VISITAS
        // --------------------------
        // Leer el contador guardado
        contadorVisitas = prefs.getInt("contador_visitas", 0)
        // Sumar +1 porque abriste la app
        contadorVisitas += 1
        // Guardar el nuevo valor
        prefs.edit().putInt("contador_visitas", contadorVisitas).apply()
        // Mostrar el valor
        tvContador.text = "Contador: $contadorVisitas"
        // Resetear con el botón
        btnResetear.setOnClickListener {
            contadorVisitas = 0
            prefs.edit().putInt("contador_visitas", contadorVisitas).apply()
            tvContador.text = "Contador: $contadorVisitas"
        }

        // --------------------------
        // SWITCH MODO OSCURO/CLARO
        // --------------------------
        // Leer preferencia guardada
        esModoOscuro = prefs.getBoolean("modo_oscuro", false)
        switchModoOscuro.isChecked = esModoOscuro
        // Aplicar el tema al iniciar
        aplicarTema(esModoOscuro)
        // Listener del Switch
        switchModoOscuro.setOnCheckedChangeListener { _, isChecked ->
            esModoOscuro = isChecked
            prefs.edit().putBoolean("modo_oscuro", esModoOscuro).apply()
            aplicarTema(esModoOscuro)
        }

        // --------------------------
        // NOTICIAS Y RECYCLERVIEW
        // --------------------------
        listaNoticias = mutableListOf(
            Noticia(
                "Google presenta Android 15",
                "Google anunció nuevas funciones de IA en Android 15.",
                "https://www.xataka.com/android/google-presenta-android-15"
            ),
            Noticia(
                "Lanzan nuevo chip Snapdragon",
                "Qualcomm lanza el Snapdragon 8 Gen 3 para móviles de gama alta.",
                "https://www.xataka.com/moviles/snapdragon-8-gen-3"
            ),
            Noticia(
                "Avances en baterías",
                "Nuevas baterías prometen durar el doble en smartphones.",
                "https://www.xataka.com/energia/baterias-nuevas"
            )
        )
        recyclerNoticias = findViewById(R.id.recyclerNoticias)
        recyclerNoticias.layoutManager = LinearLayoutManager(this)
        adapter = NoticiaAdapter(listaNoticias) { view, position ->
            posicionSeleccionada = position
            view.showContextMenu()
        }
        recyclerNoticias.adapter = adapter
        registerForContextMenu(recyclerNoticias)

        val btnPerfil = findViewById<Button>(R.id.btnPerfil)
        btnPerfil.setOnClickListener {
            val intent = Intent(this, PerfilActivity::class.java)
            startActivity(intent)
        }

    }

    // --------------------------------
    // Opciones del menú (Options Menu)
    // --------------------------------
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_options, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_acerca -> {
                Toast.makeText(this, "Acerca de MenuWebView", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.menu_salir -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // --------------------------------
    // Menú contextual (Context Menu)
    // --------------------------------
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.menu_context, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_abrir_webview -> {
                val intent = Intent(this, WebViewActivity::class.java)
                intent.putExtra("url", listaNoticias[posicionSeleccionada].url)
                startActivity(intent)
                true
            }
            R.id.menu_compartir -> {
                Toast.makeText(this, "Compartir: ${listaNoticias[posicionSeleccionada].titulo}", Toast.LENGTH_SHORT).show()
                // Aquí puedes implementar la función de compartir
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun aplicarTema(modoOscuro: Boolean) {
        val fondo: Int
        val colorTexto: Int

        if (modoOscuro) {
            fondo = Color.parseColor("#333333") // Gris oscuro
            colorTexto = Color.WHITE
        } else {
            fondo = Color.WHITE
            colorTexto = Color.BLACK
        }

        // Cambia fondo de toda la pantalla
        findViewById<View>(android.R.id.content).rootView.setBackgroundColor(fondo)

        // Cambia color de textos principales (agrega más si tienes otros)
        tvContador.setTextColor(colorTexto)
        switchModoOscuro.setTextColor(colorTexto)
        btnResetear.setTextColor(colorTexto)
    }

}
