
<!-- BAR CHART -->
<div class="box box-success">
    <div class="box-header with-border">
        <h3 class="box-title">Venta Semanal</h3>

        <div class="box-tools pull-right">
            <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
            </button>
            <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
        </div>
    </div>
    <div class="box-body">
        <div class="chart">
            <canvas id="barChart" style="height:200px"></canvas>
            <div class='legendclass' id="legend"></div>
        </div>
    </div>
    <!-- /.box-body -->
</div>
<!-- /.box -->

<style>    
        .legendclass ul {
            list-style: none;
        }
        .legendclass ul li {
            display: block;
            padding-left: 30px;
            position: relative;
            margin-bottom: 4px;
            border-radius: 5px;
            padding: 2px 8px 2px 28px;
            font-size: 14px;
            cursor: default;
            height:20px;
            width: 140px;
            -webkit-transition: background-color 200ms ease-in-out;
            -moz-transition: background-color 200ms ease-in-out;
            -o-transition: background-color 200ms ease-in-out;
            transition: background-color 200ms ease-in-out;
        }
        .legendclass li span {
            display: block;
            position: absolute;
            left: 0;
            top: 0;
            width: 20px;
            border-radius: 5px;
            height:20px
        }

        .legendclass li span.bar-legend-text {
            left:25px;
            width:120px;
        }
    
</style>


