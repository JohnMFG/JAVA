<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreatePhoneTable1 extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('phone', function (Blueprint $table) {
            $table->id();
            $table->string('name');
            $table->integer('storage');
            $table->integer('price');
            $table->foreignId('manufacturer_id');
            $table->timestamps();

            $table->foreign('manufacturer_id')->references('id')->on('manufacturer');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('phone');
    }
}
