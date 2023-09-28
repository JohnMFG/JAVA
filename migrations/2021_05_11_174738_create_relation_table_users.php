<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateRelationTableUsers extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::table('users', function (Blueprint $table) {
            $table->foreignId('operator_id')->after('password')->nullable();
            $table->foreignId('phone_id')->after('operator_id')->nullable();
            $table->integer('phone_number')->after('phone_id')->nullable();

            $table->foreign('phone_id')->references('id')->on('phone');
            $table->foreign('operator_id')->references('id')->on('operator');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::table('users', function (Blueprint $table) {
            //
        });
    }
}
