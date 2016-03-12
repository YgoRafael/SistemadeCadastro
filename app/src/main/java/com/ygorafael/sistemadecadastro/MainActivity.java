package com.ygorafael.sistemadecadastro;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;


    class Registro {
    private String nome;
    private String endereco;
    private String telefone;

    public Registro(String nome, String endereco, String telefone)
    {
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
    }
    public String getNome() { return nome; }
    public String getTelefone() { return telefone; }
    public String getEndereco() { return endereco; }
}

public class MainActivity extends Activity {
    private ArrayList<Registro> aRegistro;
    TelaPrincipal tela_principal;
    TelaCadastroUsuario tela_cadastro;
    TelaListagemUsuarios tela_listagem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        aRegistro = new ArrayList<>();

        tela_principal = new TelaPrincipal(this);
        tela_cadastro = new TelaCadastroUsuario(this, tela_principal);
        tela_listagem = new TelaListagemUsuarios(this, tela_principal);
        tela_principal.setTelaCadastro(tela_cadastro);
        tela_principal.setTelaListagem(tela_listagem);
        tela_principal.CarregarTela();
    }
    public ArrayList<Registro> getRegistros() {
        return aRegistro;

    }

    public void ExibirMensagem(String msg) {
        AlertDialog.Builder d = new
                AlertDialog.Builder(MainActivity.this);
        d.setTitle("Aviso");
        d.setMessage(msg);
        d.setNeutralButton("OK", null);
        d.show();
    }
}

    class TelaPrincipal {
    MainActivity act;
    Button btcadastrar_usuario;
    Button bt_listar_usuarios_cadastrados;
    TelaCadastroUsuario tela_cadastro;
    TelaListagemUsuarios tela_listagem;

        public TelaPrincipal(MainActivity act) {
        this.act = act;
    }
    public void CarregarTela()
    {
        act.setContentView(R.layout.tela_principal);
        btcadastrar_usuario = (Button)
                act.findViewById(R.id.btcadastrar_usuario);
        bt_listar_usuarios_cadastrados = (Button)
                act.findViewById(R.id.bt_listar_usuarios_cadastrados);
        btcadastrar_usuario.setOnClickListener(new


View.OnClickListener() {
 @Override
 public void onClick(View view) {
tela_cadastro.CarregarTela();
}
 });
        bt_listar_usuarios_cadastrados.setOnClickListener(new

 View.OnClickListener() {
@Override
 public void onClick(View view) {
 tela_listagem.CarregarTela();
 }
});
 }

    public void setTelaCadastro(TelaCadastroUsuario tela_cadastro)
    {
        this.tela_cadastro = tela_cadastro;
    }
    public void setTelaListagem(TelaListagemUsuarios tela_listagem)
    {
        this.tela_listagem = tela_listagem;
    }
}

    class TelaCadastroUsuario {
    MainActivity act;
    EditText ednome,edendereco, edtelefone;
    Button btcadastrar, btcancelar_cadastro;
    TelaPrincipal tela_principal;

        public TelaCadastroUsuario(MainActivity act, TelaPrincipal
            tela_principal)
    {
        this.act = act;
        this.tela_principal = tela_principal;
    }
    public void CarregarTela()
    {
        act.setContentView(R.layout.cadastro_de_usuario);
        ednome = (EditText) act.findViewById(R.id.ednome);
        edtelefone = (EditText) act.findViewById(R.id.edtelefone);

        edendereco = (EditText) act.findViewById(R.id.edendereco);
        btcadastrar = (Button) act.findViewById(R.id.btcadastrar);
        btcancelar_cadastro = (Button)
                act.findViewById(R.id.btcancelar_cadastro);
        btcadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialogo = new
                        AlertDialog.Builder(act);
                dialogo.setTitle("Aviso");
                dialogo.setMessage("Cadastrar usuário ?");
                dialogo.setNegativeButton("Não", null);
                dialogo.setPositiveButton("Sim", new
                        DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                String nome =
                                        ednome.getText().toString();
                                String telefone =
                                        edtelefone.getText().toString();
                                String endereco =
                                        edendereco.getText().toString();
                                act.getRegistros().add(new
                                        Registro(nome,telefone,endereco));
                                act.ExibirMensagem("Cadastro efetuado com sucesso");
                                        tela_principal.CarregarTela();
                            }
                        });
                dialogo.show();
            }
        });
        btcancelar_cadastro.setOnClickListener(new
                                                       View.OnClickListener() {
                                                           @Override

public void onClick(View view) {
                                                               AlertDialog.Builder dialogo = new
                                                                       AlertDialog.Builder(act);
                                                               dialogo.setTitle("Aviso");
                                                               dialogo.setMessage("Sair do cadastro ?");

                                                               dialogo.setPositiveButton("Sim", new
                                                                       DialogInterface.OnClickListener() {
                                                                           @Override
                                                                           public void onClick(DialogInterface dialog,
                                                                                               int which) {
                                                                               tela_principal.CarregarTela();
                                                                           }
                                                                       });
                                                               dialogo.show();
                                                           }
                                                       });
    }
}

    class TelaListagemUsuarios {
    MainActivity act;
    TelaPrincipal tela_principal;
    Button btanterior, btproximo, btfechar;
    TextView txtnome, txttelefone, txtendereco, txtstatus;
    int index;
    public TelaListagemUsuarios(MainActivity act,TelaPrincipal
            tela_principal) {
        this.act = act;
        this.tela_principal = tela_principal;
        index = 0;
    }

    public void CarregarTela()
    {
//Antes de carregar a tela, verifica se existe registros
//inseridos

        if(act.getRegistros().size() == 0)
        {
            (new AlertDialog.Builder(act))
                    .setTitle("Aviso")
                    .setMessage("Não existe nenhum registro cadastrado.")
                    .setNeutralButton("OK", null)
                    .show();
            return;
        }
        act.setContentView(R.layout.listagem_usuarios_cadastros);
        btanterior = (Button) act.findViewById(R.id.btanterior);
        btproximo = (Button) act.findViewById(R.id.btproximo);
        btfechar = (Button) act.findViewById(R.id.btfechar);
        txtnome = (TextView) act.findViewById(R.id.txtnome);
        txtendereco = (TextView) act.findViewById(R.id.txtendereco);
        txttelefone = (TextView) act.findViewById(R.id.txttelefone);
        txtstatus = (TextView) act.findViewById(R.id.txtstatus);
        PreencheCampos(index);
        AtualizaStatus(index);
        btanterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(index > 0)
                {
                    index--;
                    PreencheCampos(index);
                    AtualizaStatus(index);
                }
            }
        });
        btproximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(index < act.getRegistros().size() - 1)
                {
                    index++;
                    PreencheCampos(index);
                    AtualizaStatus(index);
                }


            }
        });
        btfechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tela_principal.CarregarTela();
            }
        });
    }
    private void PreencheCampos(int idx)
    {
        txtnome.setText(act.getRegistros().get(idx).getNome());
        txttelefone.setText(act.getRegistros().get(idx).getTelefone());
        txtendereco.setText(act.getRegistros().get(idx).getEndereco());
    }
    private void AtualizaStatus(int idx)
    {
        int total = act.getRegistros().size();
        txtstatus.setText("Registros : " + (idx+1) + "/" + total);
    }
}