function bissexto(ano) {
    if (ano % 4 == 0) {
        return true;
    }
    return false;
}
function diaSemana(nSemana) {
    dia = "";
    switch (nSemana) {
        case 1:
            dia = "Domingo";
            break;
        case 2:
            dia = "Segunda";
            break;
        case 3:
            dia = "Terça";
            break;
        case 4:
            dia = "Quarta";
            break;
        case 5:
            dia = "Quinta";
            break;
        case 6:
            dia = "Sexta";
            break;
        case 7:
            dia = "Sábado";
            break;
    }
    return dia;
}

function diasMes(mes, ano) {
    dias = 0;
    if (mes == 1) {
        dias = 31;
    }
    if (mes == 2) {
        if (bissexto(ano) == true) {
            dias = 29;
        }
        else {
            dias = 28;
        }
    }
    if (mes == 3) {
        dias = 31;
    }
    if (mes == 4) {
        dias = 30;
    }
    if (mes == 5) {
        dias = 31;
    }
    if (mes == 6) {
        dias = 30;
    }
    if (mes == 7) {
        dias = 31;
    }
    if (mes == 8) {
        dias = 31;
    }
    if (mes == 9) {
        dias = 30;
    }
    if (mes == 10) {
        dias = 31;
    }
    if (mes == 11) {
        dias = 30;
    }
    if (mes == 12) {
        dias = 31;
    }
    return dias;
}
const birth = () => {
    const date = new Date()
    let dia = date.getDate()
    let mes = date.getMonth() + 1
    let ano = date.getFullYear()
    let ndSemana = date.getDay() + 1
    let diaN = document.nascimento.elements["dia"].value;
    let mesN = document.nascimento.elements["mes"].value;
    let anoN = document.nascimento.elements["ano"].value;

    if (diaN == "" || mesN == "" || anoN == "") {
        alert("Insira todos os dados")
        return;
    }

    let idade = (ano - anoN)

    if (mesN > mes) {
        document.write('<p style = "font-size:200%;position: absolute;top: 50%;left: 50%;transform:translate(-50%, -50%);">');
        if (bissexto(ano) == false && diaN == 29 && mesN == 2) {
            document.writeln("Você não faz aniversário nesse ano<br>")
            aux = 4 - (ano % 4);
            document.writeln("Você só fará aniversário daqui " + aux + " ano(s).<br>");
        } else {
            let dias = 0;
            dias += diasMes(mes, ano) - dia + parseInt(diaN)
            while ((mesN - 1) > mes) {
                mes += 1
                dias += diasMes(mes, ano)
            }
            document.writeln("Faltam " + dias + " dias para o seu aniversário<br>");
            nDiaAniver = ndSemana + (dias % 7);
            if (nDiaAniver > 7) { nDiaAniver -= 7; }
            diaSemanaAniver = diaSemana(nDiaAniver);
            document.writeln("Você faz aniversário em: " + diaSemanaAniver + "<br>");
            while (idade > 0) {
                idade -= 1;
                nDiaAniver -= 1;
                if (nDiaAniver <= 0) {
                    nDiaAniver += 7;
                }
                if (bissexto(ano) == true) {
                    nDiaAniver -= 1;
                    if (nDiaAniver <= 0) {
                        nDiaAniver += 7;
                    }
                }
                ano -= 1;
            }
            diaNascimento = diaSemana(nDiaAniver);
            document.writeln("Você nasceu em: " + diaNascimento + "<br>");
        }
        document.write('</p>')
    }
    else if (mesN < mes) {
        document.write('<p style = "font-size:200%;position: absolute;top: 50%;left: 50%;transform:translate(-50%, -50%);">');
        if (bissexto(ano) == false && diaN == 29 && mesN == 2) {
            document.writeln("Você não faz aniversário nesse ano<br>");
            aux = 4 - (ano % 4);
            document.writeln("Você só fará aniversário daqui " + aux + " ano(s).<br>");
        } else {
            dias = 0;
            dias += diasMes(parseInt(mesN), ano) - parseInt(diaN) + dia

            while (parseInt(mesN) < (mes - 1)) {
                mes -= 1;
                dias += diasMes(mes, ano);
            }
            document.writeln("Se passaram " + dias + " dias do seu aniversário<br>");

            nDiaAniver = ndSemana - (dias % 7);
            if (nDiaAniver <= 0) { nDiaAniver += 7; }
            diaSemanaAniver = diaSemana(nDiaAniver);
            document.writeln("Você fez aniversário em: " + diaSemanaAniver + "<br>");
            while (idade > 0) {
                idade -= 1;
                nDiaAniver -= 1;
                if (nDiaAniver <= 0) {
                    nDiaAniver += 7;
                }
                if (bissexto(ano) == true) {
                    nDiaAniver -= 1;
                    if (nDiaAniver <= 0) {
                        nDiaAniver += 7;
                    }
                }
                ano -= 1;
            }
            diaNascimento = diaSemana(nDiaAniver);
            document.writeln("Você nasceu em: " + diaNascimento + "<br>");
        }
        document.write('</p>')
    }
    else if (mesN == mes) {
        document.write('<p style = "font-size:200%;position: absolute;top: 50%;left: 50%;transform:translate(-50%, -50%);">');
        dias = Math.abs(diaN - dia);
        if (diaN > dia) {
            document.writeln("Faltam " + dias + " dias para seu aniversário<br>");
        }
        else if (diaN < dia) {
            document.writeln("Se passaram " + dias + " dias do seu aniversário<br>");
        }
        else if (diaN == dia) {
            document.writeln("Parabéns pelo seu aniversario!<br>");
        }
        if (dia > diaN) { nDiaAniver = ndSemana - (dias % 7); }
        else { nDiaAniver = (dias % 7) + ndSemana; }
        if (nDiaAniver > 7) { nDiaAniver -= 7; }
        diaSemanaAniver = diaSemana(nDiaAniver);
        document.writeln("Você faz aniversário em: " + diaSemanaAniver + "<br>");
        while (idade > 0) {
            idade -= 1;
            nDiaAniver -= 1;
            if (nDiaAniver <= 0) {
                nDiaAniver += 7;
            }
            if (bissexto(ano) == true) {
                nDiaAniver -= 1;
            }
            ano -= 1;
        }
        diaNascimento = diaSemana(nDiaAniver);
        document.writeln("Você nasceu em: " + diaNascimento);
        document.write('</p>');
    }
}
